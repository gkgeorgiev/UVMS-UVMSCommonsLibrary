package eu.europa.ec.fisheries.uvms.rest.resource;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import eu.europa.ec.fisheries.uvms.rest.dto.ResponseDto;


public class UnionVMSResource {

	public Response createSuccessResponse() {
		ResponseDto dto = new ResponseDto(HttpServletResponse.SC_OK);
		Response response = Response.status(HttpServletResponse.SC_OK).entity(dto).build();
		return response;
	}

	public <T> Response createSuccessResponse(T data) {
		ResponseDto<T> dto = new ResponseDto<T>(data, HttpServletResponse.SC_OK);
		Response response = Response.status(HttpServletResponse.SC_OK).entity(dto).build();
		return response;
	}
	
	public Response createErrorResponse() {
		ResponseDto dto = new ResponseDto(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		Response response = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).entity(dto).build();
		return response;
	}
	
	public Response createErrorResponse(String errorMsgCode) {
		ResponseDto dto = new ResponseDto(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMsgCode);
		Response response = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).entity(dto).build();
		return response;
	}
}
