package com.thoughtworks.gaia.product.endpoint;

import com.eureka2.shading.codehaus.jackson.map.ObjectMapper;
import com.thoughtworks.gaia.product.entity.A;
import com.thoughtworks.gaia.product.entity.B;
import com.thoughtworks.gaia.product.service.BService;
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
@Api(value = "B api", description = "access to B resource")
@Path("Bs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BEndPoint {


    @Autowired
    private BService bService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Path("/{b_id}")
    @ApiOperation(value = "get B by id", response = B.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GET
    public Response getB(@PathParam("b_id") Long b_id) {
        B b = bService.getB(b_id);
        return Response.ok().entity(b).build();
    }

    @Path("")
    @ApiOperation(value = "add B ", response = B.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "failed")
    })
    @POST
    public Response addB(@RequestParam String aJson) {
        B b = new B();
        try {
            b = objectMapper.readValue(aJson, B.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok().entity(bService.addB(b)).build();
    }

    @Path("/{b_id}")
    @ApiOperation(value = "update B", response = B.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "failed")
    })
    @PUT
    public Response updateB(
            @PathParam("b_id") Long b_id,
            @RequestParam String aJson
    ) {
        B b = new B();

        try {
            b = objectMapper.readValue(aJson, B.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        b.setId(b_id);
        return Response.ok().entity(bService.updateB(b)).build();
    }

    @Path("/{b_id}")
    @ApiOperation(value = "delete B", response = B.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "failed")
    })
    @DELETE
    public Response delB(@PathParam("b_id") Long b_id) {
        B b = bService.deleteB(b_id);
        return Response.ok().entity(b).build();
    }
}