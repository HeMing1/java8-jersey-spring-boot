package com.thoughtworks.gaia.product.endpoint;

import com.eureka2.shading.codehaus.jackson.JsonParseException;
import com.eureka2.shading.codehaus.jackson.map.JsonMappingException;
import com.eureka2.shading.codehaus.jackson.map.ObjectMapper;
import com.thoughtworks.gaia.product.entity.A;
import com.thoughtworks.gaia.product.service.AService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Component
@Api(value = "A api", description = "access to A resource")
@Path("As")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AEndPoint {


    @Autowired
    private AService aService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Path("/{a_id}")
    @ApiOperation(value = "get A by id", response = A.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GET
    public Response getA(@PathParam("a_id") Long a_id) {
        A a = aService.getA(a_id);
        return Response.ok().entity(a).build();
    }

    @Path("")
    @ApiOperation(value = "add A ", response = A.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "failed")
    })
    @POST
    public Response addA(@RequestParam String aJson) throws JsonParseException {
        A a = new A();
        try {
            a = objectMapper.readValue(aJson, A.class);
        } catch (JsonParseException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok().entity(aService.addA(a)).build();
    }

    @Path("/{a_id}")
    @ApiOperation(value = "update a", response = A.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "failed")
    })
    @PUT
    public Response updateA(
            @PathParam("a_id") Long a_id,
            @RequestParam String aJson
    ) throws JsonParseException {
        A a = null;
        try {
            a = objectMapper.readValue(aJson, A.class);
        } catch (JsonParseException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        a.setId(a_id);
        aService.updateA(a);
        return Response.ok().entity(a).build();
    }

    @Path("/{a_id}")
    @ApiOperation(value = "delete A", response = A.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "failed")
    })
    @DELETE
    public Response delA(@PathParam("a_id") Long a_id) {
        A a = aService.deleteA(a_id);
        return Response.ok().entity(a).build();
    }
}