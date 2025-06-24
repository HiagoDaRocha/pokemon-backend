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

    // Endpoint HTTP GET que retorna informações de um Pokémon específico pelo nome
    @GET
    @Produces(MediaType.APPLICATION_JSON) // Retorna dados no formato JSON
    @Path("/{name}") // URL será /{nome_do_pokemon}
    public Response getPokemon(@PathParam("name") String name) {
        
        // Faz a busca do Pokémon na API, transformando o nome para minúsculo
        Pokemon pokemon = pokeApiService.getPokemon(name.toLowerCase());

        // Verifica se o Pokémon está dentro da primeira geração (ID até 151)
        if (pokemon.getId() <= 151) {
            // Converte o modelo Pokemon para um DTO (objeto de transporte)
            PokemonDTO dto = PokemonDTO.fromModel(pokemon);
            // Retorna resposta HTTP 200 (OK) com os dados no corpo
            return Response.ok(dto).build();
        } else {
            // Se não for da primeira geração, retorna HTTP 404 (Not Found)
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Pokémon fora da primeira geração")
                .build();
        }
    }


    // Mapeia os tipos em português para o equivalente em inglês
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

   // Endpoint HTTP GET que retorna informações sobre o tipo (força, fraqueza, etc.)
    @GET
    @Path("/tipo/{name}") // URL será /tipo/{nome_do_tipo}
    @Produces(MediaType.APPLICATION_JSON) // Retorna JSON
    public Response getTipoInfo(@PathParam("name") String name) {

        // Traduz o tipo do português para inglês usando o mapa
        String tipoEmIngles = tipoPtToEn.getOrDefault(name.toLowerCase(), name.toLowerCase());

        // Busca as informações do tipo na API
        TypeResponse tipoResponse = pokeApiService.getType(tipoEmIngles);

        // Lista de tipos que fazem parte da primeira geração
        List<String> tiposGen1 = List.of(
            "normal", "fire", "water", "grass", "electric", "ice",
            "fighting", "poison", "ground", "flying", "psychic",
            "bug", "rock", "ghost", "dragon"
        );

        // Verifica se o tipo está na primeira geração
        if (!tiposGen1.contains(tipoEmIngles)) {
            // Se não estiver, retorna 404 (não encontrado)
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Tipo fora da primeira geração ou inválido")
                .build();
        }

        // Processa as relações de dano — quem ele é forte contra
        List<String> forteContra = tipoResponse.getDamage_relations().getDouble_damage_to().stream()
            .map(typeInfo -> typeInfo.getName()) // Extrai o nome
            .filter(tiposGen1::contains) // Filtra apenas tipos da primeira geração
            .toList();

        // Quem ele recebe mais dano (fraquezas)
        List<String> fracoContra = tipoResponse.getDamage_relations().getDouble_damage_from().stream()
            .map(typeInfo -> typeInfo.getName())
            .filter(tiposGen1::contains)
            .toList();

        // Quem ele causa meio dano (resistências ofensivas)
        List<String> meioForteContra = tipoResponse.getDamage_relations().getHalf_damage_to().stream()
            .map(typeInfo -> typeInfo.getName())
            .filter(tiposGen1::contains)
            .toList();

        // De quem ele recebe meio dano (resistências defensivas)
        List<String> meioFracoContra = tipoResponse.getDamage_relations().getHalf_damage_from().stream()
            .map(typeInfo -> typeInfo.getName())
            .filter(tiposGen1::contains)
            .toList();

        // Tipos contra os quais ele não causa nenhum dano
        List<String> naoDanoContra = tipoResponse.getDamage_relations().getNo_damage_to().stream()
            .map(typeInfo -> typeInfo.getName())
            .filter(tiposGen1::contains)
            .toList();

        // Tipos dos quais ele não recebe dano
        List<String> naoDanoRecebe = tipoResponse.getDamage_relations().getNo_damage_from().stream()
            .map(typeInfo -> typeInfo.getName())
            .filter(tiposGen1::contains)
            .toList();

        // Cria o DTO com todas essas informações
        TypeResponseDTO dto = new TypeResponseDTO(
            name.toLowerCase(), // Nome do tipo pesquisado
            forteContra,        // Lista de tipos contra os quais é forte
            fracoContra,        // Lista de tipos contra os quais é fraco
            meioForteContra,    // Lista de tipos que recebe meio dano ofensivamente
            meioFracoContra,    // Lista de tipos que recebe meio dano defensivamente
            naoDanoContra,      // Tipos que não recebem dano dele
            naoDanoRecebe       // Tipos dos quais não recebe dano
        );

        // Retorna HTTP 200 (OK) com o DTO no corpo
        return Response.ok(dto).build();
    }



}
