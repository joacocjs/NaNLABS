package com.nanlabs;

import com.nanlabs.configurations.Constants;
import com.nanlabs.configurations.TrelloConfigurations;
import com.nanlabs.controller.ContractController;
import com.nanlabs.dto.CardRequestDTO;
import com.nanlabs.dto.CardResponseDTO;
import com.nanlabs.dto.MembersResponseDTO;
import com.nanlabs.enums.HttpCode;
import com.nanlabs.services.impl.TrelloApiServiceImpl;
import com.nanlabs.utils.Utils;
import com.nanlabs.utils.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.net.URI;
import java.sql.Array;
import java.util.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(TestConfigurations.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@AutoConfigureMockMvc(addFilters = false)
class NaNlabsApplicationTests {

    @Mock
    ContractController contractController;

    @InjectMocks
    TrelloApiServiceImpl trelloApiService;

    @Mock
    TrelloConfigurations configService;

    @Mock
    RestTemplate restTemplate;

    private MockMvc mockMvc;

    /**
     * Issue test cases
     * TODO: Branches cases
     */
    @Test
    void testAddIssueCardSuccess() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mockito
                .when(restTemplate.postForObject(this.getAddCardURLTest(""),
                        new HttpEntity<>(getCardResponseDTO_Ok(), headers), CardResponseDTO.class))
                .thenReturn(getCardResponseDTO_Ok());

        when(trelloApiService.addCard(getIssueCardRequestDTO()))
                .thenReturn(getCardResponseDTO_Ok());

        doReturn(getCardResponseDTO_Ok())
                .when(contractController)
                .addCard(any(CardRequestDTO.class));


        CardResponseDTO response =
                contractController.addCard(getIssueCardRequestDTO());

        assertThat(response, instanceOf(CardResponseDTO.class));
        assertNotNull(getCardResponseDTO_Ok().getId());
        assertEquals(getIssueCardRequestDTO().getTitle(), response.getName());
        assertEquals(getIssueCardRequestDTO().getType(), "issue");

    }

    @Test
    void testAddCardBadRequest() {
        doThrow(new BusinessException(20,"Validation", "Error", HttpCode.BAD_REQUEST))
                .when(contractController)
                .addCard(getCardRequestDTO_Bad_Body());

        BusinessException be =
                assertThrows(
                        BusinessException.class,
                        () -> {
                            trelloApiService.addCard(getCardRequestDTO_Bad_Body());
                        });

        assertEquals(HttpStatus.BAD_REQUEST, be.getHttpStatus());
    }

    @Test
    void testAddIssueCardError_Bad_Request_Body() {
        doThrow(new BusinessException(20,"Validation", "Error", HttpCode.BAD_REQUEST))
                .when(contractController)
                .addCard(getIssueCardRequestDTO_Issue_Error());

        BusinessException be =
                assertThrows(
                        BusinessException.class,
                        () -> {
                            trelloApiService.addCard(getIssueCardRequestDTO_Issue_Error());
                        });

        assertEquals(HttpStatus.BAD_REQUEST, be.getHttpStatus());
    }

    /**
     * Bug Test cases
     * * TODO: Branches cases
     */
    @Test
    void testAddBugCardSuccess() {

        doReturn(getCardResponseDTO_Ok())
                .when(contractController)
                .addCard(any(CardRequestDTO.class));

        CardResponseDTO response =
                contractController.addCard(getBugCardRequestDTO());

        assertThat(response, instanceOf(CardResponseDTO.class));
        assertNotNull(getCardResponseDTO_Ok().getId());
        assertTrue(getCardResponseDTO_Ok().getIdLabels().size()>0);
        assertTrue(getCardResponseDTO_Ok().getIdLabels().contains("Bug"));
        assertTrue(getCardResponseDTO_Ok().getIdMembers().size()>0);
        assertEquals(getBugCardRequestDTO().getDescription(), response.getDesc());
        assertEquals(getBugCardRequestDTO().getType(), "bug");

    }

    @Test
    void testAddBugCardError_Bad_Request_Body() {
        doThrow(new BusinessException(20,"Validation", "Error", HttpCode.BAD_REQUEST))
                .when(contractController)
                .addCard(getBugCardRequestDTO_Bad_Body());

        BusinessException be =
                assertThrows(
                        BusinessException.class,
                        () -> {
                            trelloApiService.addCard(getBugCardRequestDTO_Bad_Body());
                        });

        assertEquals(HttpStatus.BAD_REQUEST, be.getHttpStatus());
    }

    /**
     * TODO: Task Test cases
     */

    private CardRequestDTO getCardRequestDTO_Bad_Body(){
        return CardRequestDTO.builder()
                .build();
    }
    private CardRequestDTO getIssueCardRequestDTO(){
        return CardRequestDTO.builder()
                .title(TestUtils.RESPONSE_DTO_NAME)
                .description(TestUtils.RESPONSE_DTO_DESC)
                .type("issue")
                .build();
    }
    private CardRequestDTO getIssueCardRequestDTO_Issue_Error(){
        return CardRequestDTO.builder()
                .type("issue")
                .build();
    }
    private CardResponseDTO getCardResponseDTO_Ok(){
        return CardResponseDTO.builder()
                .id(TestUtils.RESPONSE_DTO_ID)
                .name(TestUtils.RESPONSE_DTO_NAME)
                .idList(TestUtils.RESPONSE_DTO_ID_LIST)
                .desc(TestUtils.RESPONSE_DTO_DESC)
                .idLabels(TestUtils.RESPONSE_DTO_LABELS)
                .idMembers(TestUtils.RESPONSE_DTO_MEMBERS)
                .build();
    }

    private CardRequestDTO getBugCardRequestDTO(){
        return CardRequestDTO.builder()
                .title(Utils.getBugTitle())
                .description(TestUtils.RESPONSE_DTO_DESC)
                .type("bug")
                .build();
    }
    private CardRequestDTO getBugCardRequestDTO_Bad_Body(){
        return CardRequestDTO.builder()
                .type("bug")
                .build();
    }

    public URI getAddCardURLTest(String type){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idList", (type=="todo")? configService.getTodoList() : configService.getTasksList());
        urlParams.put("key", configService.getAppkey());
        urlParams.put("token", configService.getToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constants.URL_POST_CARD + type);
        return builder.buildAndExpand(urlParams).toUri();
    }
}
