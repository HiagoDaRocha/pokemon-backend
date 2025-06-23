package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import org.acme.dto.PokemonDTO;
import org.acme.dto.TypeResponseDTO;
import org.acme.model.Pokemon;
import org.acme.model.TypeResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/pokemon")
public class PokemonResource {

    @Inject
    @RestClient
    PokeApiService pokeApiService;

   @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public Response getPokemon(@PathParam("name") String name) {
        Pokemon pokemon = pokeApiService.getPokemon(name.toLowerCase());

        if (pokemon.getId() <= 151) {
            PokemonDTO dto = PokemonDTO.fromModel(pokemon);
            return Response.ok(dto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Pokémon fora da primeira geração").build();
        }
    }


    private static final Map<String, String> tipoPtToEn = Map.ofEntries(
        Map.entry("normal", "normal"),
        Map.entry("fogo", "fire"),
        Map.entry("água", "water"),
        Map.entry("agua", "water"),
        Map.entry("grama", "grass"),
        Map.entry("elétrico", "electric"),
        Map.entry("eletrico", "electric"),
        Map.entry("gelo", "ice"),
        Map.entry("lutador", "fighting"),
        Map.entry("veneno", "poison"),
        Map.entry("terra", "ground"),
        Map.entry("voador", "flying"),
        Map.entry("psíquico", "psychic"),
        Map.entry("psiquico", "psychic"),
        Map.entry("inseto", "bug"),
        Map.entry("pedra", "rock"),
        Map.entry("fantasma", "ghost"),
        Map.entry("dragão", "dragon"),
        Map.entry("dragao", "dragon")
    );

    @GET
    @Path("/tipo/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoInfo(@PathParam("name") String name) {
            // Traduz do PT para EN
            String tipoEmIngles = tipoPtToEn.getOrDefault(name.toLowerCase(), name.toLowerCase());

            TypeResponse tipoResponse = pokeApiService.getType(tipoEmIngles);

            List<String> tiposGen1 = List.of(
                "normal", "fire", "water", "grass", "electric", "ice",
                "fighting", "poison", "ground", "flying", "psychic",
                "bug", "rock", "ghost", "dragon"
            );

                if (!tiposGen1.contains(tipoEmIngles)) {
                    return Response.status(Response.Status.NOT_FOUND)
                    .entity("Tipo fora da primeira geração ou inválido")
                    .build();
                }

            List<String> forteContra = tipoResponse.getDamage_relations().getDouble_damage_to().stream()
                .map(typeInfo -> typeInfo.getName())
                .filter(tiposGen1::contains)
                .toList();

            List<String> fracoContra = tipoResponse.getDamage_relations().getDouble_damage_from().stream()
                .map(typeInfo -> typeInfo.getName())
                .filter(tiposGen1::contains)
                .toList();

            List<String> meioForteContra = tipoResponse.getDamage_relations().getHalf_damage_to().stream()
                .map(typeInfo -> typeInfo.getName())
                .filter(tiposGen1::contains)
                .toList();

            List<String> meioFracoContra = tipoResponse.getDamage_relations().getHalf_damage_from().stream()
                .map(typeInfo -> typeInfo.getName())
                .filter(tiposGen1::contains)
                .toList();

            List<String> naoDanoContra = tipoResponse.getDamage_relations().getNo_damage_to().stream()
                .map(typeInfo -> typeInfo.getName())
                .filter(tiposGen1::contains)
                .toList();

            List<String> naoDanoRecebe = tipoResponse.getDamage_relations().getNo_damage_from().stream()
                .map(typeInfo -> typeInfo.getName())
                .filter(tiposGen1::contains)
                .toList();

           TypeResponseDTO dto = new TypeResponseDTO(
            name.toLowerCase(), 
            forteContra,
            fracoContra,
            meioForteContra,
            meioFracoContra,
            naoDanoContra,
            naoDanoRecebe
        );

        return Response.ok(dto).build();

    }


}
