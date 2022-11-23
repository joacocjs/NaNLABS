package com.nanlabs.controller.operations;

import com.nanlabs.utils.exceptions.ApiErrorResponse;
import com.nanlabs.dto.CardRequestDTO;
import com.nanlabs.dto.CardResponseDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

public interface ContractOperations {

    @ApiOperation(value = "Add new card")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Created"),
                    @ApiResponse(code = 400, message = "BadRequest", response = ApiErrorResponse.class),
                    @ApiResponse(code = 401, message = "Unauthorized"),
                    @ApiResponse(code = 403, message = "Forbidden"),
                    @ApiResponse(code = 404, message = "Not Found", response = ApiErrorResponse.class),
                    @ApiResponse(code = 406, message = "Not Acceptable"),
                    @ApiResponse(code = 410, message = "Gone", response = ApiErrorResponse.class),
                    @ApiResponse(
                            code = 500,
                            message = "Internal Server Error",
                            response = ApiErrorResponse.class)
            })
    CardResponseDTO addCard(@RequestBody CardRequestDTO cardRequest) throws Exception;
}
