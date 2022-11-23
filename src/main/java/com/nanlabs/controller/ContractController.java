package com.nanlabs.controller;

import com.nanlabs.controller.operations.ContractOperations;
import com.nanlabs.dto.CardRequestDTO;
import com.nanlabs.dto.CardResponseDTO;
import com.nanlabs.dto.ListResponseDTO;
import com.nanlabs.services.TrelloApiService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Api(value = "NaNLabs Api chalenge")
public class ContractController implements ContractOperations {

    @Autowired
    private TrelloApiService trelloApiService;


    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ListResponseDTO> getLists(){
        return trelloApiService.getLists();
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CardResponseDTO addCard(@RequestBody CardRequestDTO cardRequest) {
        return trelloApiService.addCard(cardRequest);
    }
}
