package org.acme;

import org.acme.model.Pokemon;
import org.acme.model.TypeResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "pokeapi")
public interface PokeApiService {

    @GET
    @Path("/pokemon/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Pokemon getPokemon(@PathParam("name") String name);

    @GET
    @Path("/type/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    TypeResponse getType(@PathParam("name") String name);
}
