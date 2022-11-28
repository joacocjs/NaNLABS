package com.nanlabs.services.impl;

import com.nanlabs.enums.HttpCode;
import com.nanlabs.utils.Utils;
import com.nanlabs.configurations.Constants;
import com.nanlabs.configurations.TrelloConfigurations;
import com.nanlabs.dto.CardRequestDTO;
import com.nanlabs.dto.CardResponseDTO;
import com.nanlabs.dto.ListResponseDTO;
import com.nanlabs.dto.MembersResponseDTO;
import com.nanlabs.services.TrelloApiService;
import com.nanlabs.utils.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class TrelloApiServiceImpl implements TrelloApiService {

    @Autowired
    private TrelloConfigurations config;

    @Autowired
    RestTemplate restTemplate;

    HashMap<String,String> labelsMap;

    public static final String VALIDATION = "Validation";

    @Override
    public List<ListResponseDTO> getLists() {
        ResponseEntity<ListResponseDTO[]> response = restTemplate.getForEntity(getBoardListsURL(), ListResponseDTO[].class);
        //System.out.println(getListCardsURL());
        return Arrays.stream(response.getBody()).toList();
    }

    public List<MembersResponseDTO> getBoardMembers() {
        ResponseEntity<MembersResponseDTO[]> response = restTemplate.getForEntity(getBoardMembersURL(), MembersResponseDTO[].class);
        //System.out.println(getListCardsURL());
        return Arrays.stream(response.getBody()).toList();
    }

    public URI getAddCardURL(String type){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idList", (type=="todo")? config.getTodoList() : config.getTasksList());
        urlParams.put("key", config.getAppkey());
        urlParams.put("token", config.getToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constants.URL_POST_CARD);
        return builder.buildAndExpand(urlParams).toUri();
    }

    public URI getBoardListsURL(){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idBoard", config.getBoard());
        urlParams.put("key", config.getAppkey());
        urlParams.put("token", config.getToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constants.URL_GET_BOARD_LISTS);
        return builder.buildAndExpand(urlParams).toUri();
    }

    public URI getListTaskCardsURL(){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idList", config.getTasksList());
        urlParams.put("key", config.getAppkey());
        urlParams.put("token", config.getToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constants.URL_GET_LIST_CARDS);
        return builder.buildAndExpand(urlParams).toUri();
    }

    public URI getListToDoCardsURL(){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idList", config.getTodoList());
        urlParams.put("key", config.getAppkey());
        urlParams.put("token", config.getToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constants.URL_GET_LIST_CARDS);
        return builder.buildAndExpand(urlParams).toUri();
    }

    public URI getBoardMembersURL(){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idBoard", config.getBoard());
        urlParams.put("key", config.getAppkey());
        urlParams.put("token", config.getToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constants.URL_GET_BOARD_MEMBERS);
        return builder.buildAndExpand(urlParams).toUri();
    }


    @Override
    public CardResponseDTO addCard(CardRequestDTO cardRequestDTO){
        mapLabels(); //Prepare labels for cards
        cardEvaluator(cardRequestDTO);
        CardResponseDTO request = cardFactory(cardRequestDTO);

        //header required for trello
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CardResponseDTO> entity = new HttpEntity<>(request, headers);
        try {
            CardResponseDTO response =
                    restTemplate.postForObject(getAddCardURL(cardRequestDTO.getType()), entity, CardResponseDTO.class);
            return response;
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void cardEvaluator(CardRequestDTO cardRequestDTO) throws BusinessException {
        if(!Objects.isNull(cardRequestDTO)) {
            if(cardRequestDTO.getType() ==null){
                throw new BusinessException(1,VALIDATION, "Type is required", HttpCode.BAD_REQUEST);
            }
            //issue validation
            if(cardRequestDTO.getType().equals("issue")){
                if(cardRequestDTO.getTitle() ==null|| cardRequestDTO.getTitle().trim().isEmpty()
                        || cardRequestDTO.getTitle() ==null || cardRequestDTO.getDescription().trim().isEmpty()){
                    throw new BusinessException(20,VALIDATION, "Fields title and description are required in body", HttpCode.BAD_REQUEST);
                }
            }
            //bug validation
            else if(cardRequestDTO.getType().equals("bug")){
                if(cardRequestDTO.getDescription() == null || cardRequestDTO.getDescription().isEmpty()){
                    throw new BusinessException(30,VALIDATION, "Field description is required in body", HttpCode.BAD_REQUEST);
                }
            }
            //task validation
            else if(cardRequestDTO.getType().equals("task")){
                if(cardRequestDTO.getTitle() == null || cardRequestDTO.getTitle().isEmpty()
                        || cardRequestDTO.getCategory() == null || cardRequestDTO.getCategory().isEmpty()){
                    throw new BusinessException(40,VALIDATION, "Fields category and title are required in body", HttpCode.BAD_REQUEST);
                }else{
                    //category validation
                    if(!labelsMap.containsKey(cardRequestDTO.getCategory())) {
                        HashMap<String, String> m = (HashMap<String, String>) labelsMap.clone();
                        m.remove("Bug");
                        throw new BusinessException(50, VALIDATION,
                                "Category invalid. Only this list is available: [" + String.join(", ", m.keySet()) + "]",
                                HttpCode.BAD_REQUEST);
                    }
                }
            }else{
                throw new BusinessException(1000,VALIDATION, "Type task invalid", HttpCode.BAD_REQUEST);
            }
        }else
            throw new BusinessException(100,VALIDATION, "Empty body", HttpCode.BAD_REQUEST);
    }

    public CardResponseDTO cardFactory(CardRequestDTO cardRequestDTO){
        CardResponseDTO resp;
        switch (cardRequestDTO.getType()) {
            case "issue":
                resp = CardResponseDTO.builder()
                        .idList(config.getTodoList())
                        .name(cardRequestDTO.getTitle())
                        .desc(cardRequestDTO.getDescription())
                        .build();
                return resp;
            case "bug":
                //get board members for be assigned randomly
                List<MembersResponseDTO> members = getBoardMembers();
                String idMemberAssigned = members.get(new Random().nextInt(members.size())).getId();

                resp = CardResponseDTO.builder()
                        .idList(config.getTasksList())
                        .name(Utils.getBugTitle())
                        .desc(cardRequestDTO.getDescription())
                        .idMembers(Arrays.asList(idMemberAssigned))
                        .idLabels(Arrays.asList(config.getBugLabel()))
                        .build();
                return resp;
            case "task":
                resp = CardResponseDTO.builder()
                        .idList(config.getTasksList())
                        .name(cardRequestDTO.getTitle())
                        .idLabels(Arrays.asList(labelsMap.get(cardRequestDTO.getCategory())))
                        .build();
                return resp;
        }
        return null;
    }

    public void mapLabels(){
        labelsMap = new HashMap<String, String>();
        labelsMap.put(Constants.LABEL_BUG, config.getBugLabel());
        labelsMap.put(Constants.LABEL_MAINTENANCE, config.getMaintenanceLabel());
        labelsMap.put(Constants.LABEL_RESEARCH, config.getResearchLabel());
        labelsMap.put(Constants.LABEL_TEST, config.getTestLabel());
    }

}
