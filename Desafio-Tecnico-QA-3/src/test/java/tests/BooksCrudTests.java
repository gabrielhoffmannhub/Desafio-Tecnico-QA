package tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BooksCrudTests {

    String baseUrl = "https://fakerestapi.azurewebsites.net/api/v1/Books";

    @Test
    public void buscarTodosOsLivros_DeveRetornar200() {
        given()
                .when()
                .get(baseUrl)
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    public void buscarLivroExistente_DeveRetornar200() {
        given()
                .when()
                .get(baseUrl + "/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void buscarLivroInexistente_DeveRetornar404() {
        given()
                .when()
                .get(baseUrl + "/999999")
                .then()
                .statusCode(404);
    }

    @Test
    public void criarLivroValido_DeveRetornar200() {
        String json =
                "{"
                        + "\"id\": 777,"
                        + "\"title\": \"Livro Teste\","
                        + "\"description\": \"Teste automático\","
                        + "\"pageCount\": 120,"
                        + "\"excerpt\": \"Texto...\","
                        + "\"publishDate\": \"2025-01-01T00:00:00\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseUrl)
                .then()
                .statusCode(200)
                .body("id", equalTo(777));
    }

    @Test
    public void criarLivroSemTitulo_DeveRetornar400() {
        String json =
                "{"
                        + "\"id\": 778,"
                        + "\"description\": \"Desc\","
                        + "\"pageCount\": 50,"
                        + "\"excerpt\": \"TXT\","
                        + "\"publishDate\": \"2025-01-01T00:00:00\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseUrl)
                .then()
                .statusCode(400);
    }

    @Test
    public void criarLivroComDataInvalida_DeveRetornar400() {
        String json =
                "{"
                        + "\"id\": 779,"
                        + "\"title\": \"Livro Bugado\","
                        + "\"description\": \"desc\","
                        + "\"pageCount\": 10,"
                        + "\"excerpt\": \"Texto\","
                        + "\"publishDate\": \"data-invalida\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseUrl)
                .then()
                .statusCode(400);
    }

    @Test
    public void atualizarLivroExistente_DeveRetornar200() {
        String json =
                "{"
                        + "\"id\": 1,"
                        + "\"title\": \"Livro Atualizado\","
                        + "\"description\": \"Novo desc\","
                        + "\"pageCount\": 123,"
                        + "\"excerpt\": \"ex\","
                        + "\"publishDate\": \"2025-01-01T00:00:00\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .put(baseUrl + "/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Livro Atualizado"));
    }

    @Test
    public void atualizarLivroInexistente_DeveRetornar404() {
        String json =
                "{"
                        + "\"id\": 999999,"
                        + "\"title\": \"Nao existe\","
                        + "\"description\": \"Teste\","
                        + "\"pageCount\": 10,"
                        + "\"excerpt\": \"e\","
                        + "\"publishDate\": \"2025-01-01T00:00:00\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .put(baseUrl + "/999999")
                .then()
                .statusCode(404);
    }

    @Test
    public void atualizarLivroComJsonInvalido_DeveRetornar400() {
        String json =
                "{"
                        + "\"title\": 12345"
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .put(baseUrl + "/1")
                .then()
                .statusCode(400);
    }

    @Test
    public void deletarLivroExistente_DeveRetornar200ou204() {
        int id = 800;

        String json =
                "{"
                        + "\"id\": "+ id +","
                        + "\"title\": \"Para deletar\","
                        + "\"description\": \"desc\","
                        + "\"pageCount\": 10,"
                        + "\"excerpt\": \"ex\","
                        + "\"publishDate\": \"2025-01-01T00:00:00\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseUrl)
                .then()
                .statusCode(200);

        given()
                .when()
                .delete(baseUrl + "/" + id)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    public void deletarLivroInexistente_DeveRetornar404() {
        given()
                .when()
                .delete(baseUrl + "/999999")
                .then()
                .statusCode(404);
    }

    @Test
    public void criarLivroComCaracteresEspeciais_DeveRetornar400() {
        String json =
                "{"
                        + "\"id\": 900,"
                        + "\"title\": \"@@@@@\","
                        + "\"description\": \"desc\","
                        + "\"pageCount\": 10,"
                        + "\"excerpt\": \"txt\","
                        + "\"publishDate\": \"2025-01-01T00:00:00\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseUrl)
                .then()
                .statusCode(400);
    }

    @Test
    public void criarLivroComCamposDoTipoErrado_DeveRetornar400() {
        String json =
                "{"
                        + "\"id\": \"abc\","
                        + "\"title\": 12345,"
                        + "\"pageCount\": \"texto\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseUrl)
                .then()
                .statusCode(400);
    }

    @Test
    public void criarLivroComJsonVazio_DeveRetornar400() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post(baseUrl)
                .then()
                .statusCode(400);
    }

    @Test
    public void criarLivroComPayloadMuitoGrande_DeveRetornar400() {
        String titulo = "A".repeat(5000);

        String json =
                "{"
                        + "\"id\": 901,"
                        + "\"title\": \"" + titulo + "\","
                        + "\"description\": \"desc\","
                        + "\"pageCount\": 10,"
                        + "\"excerpt\": \"txt\","
                        + "\"publishDate\": \"2025-01-01T00:00:00\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseUrl)
                .then()
                .statusCode(400);
    }

    @Test
    public void fluxoCompleto_CriarBuscarDeletar() {
        int id = 950;

        String json =
                "{"
                        + "\"id\": "+ id +","
                        + "\"title\": \"Fluxo\","
                        + "\"description\": \"Teste\","
                        + "\"pageCount\": 10,"
                        + "\"excerpt\": \"ex\","
                        + "\"publishDate\": \"2025-01-01T00:00:00\""
                        + "}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseUrl)
                .then()
                .statusCode(200);

        given()
                .when()
                .get(baseUrl + "/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id));

        given()
                .when()
                .delete(baseUrl + "/" + id)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));

        given()
                .when()
                .get(baseUrl + "/" + id)
                .then()
                .statusCode(404);
    }
}


