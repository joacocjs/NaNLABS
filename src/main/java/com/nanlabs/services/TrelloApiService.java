package com.nanlabs.services;

import com.nanlabs.dto.CardRequestDTO;
import com.nanlabs.dto.CardResponseDTO;
import com.nanlabs.dto.ListResponseDTO;

import java.io.IOException;
import java.util.List;

public interface TrelloApiService {

    List<ListResponseDTO> getLists();
    CardResponseDTO addCard(CardRequestDTO cardResponseDTO);
}
