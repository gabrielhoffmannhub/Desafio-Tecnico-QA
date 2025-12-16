package books.tests;

import books.client.BooksClient;
import books.utils.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class BooksReadTest extends BaseTest {

    @Test
    void buscarTodosLivrosComSucesso() {
        Response response = BooksClient.buscarTodosLivros();

        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/books-list.json"));
    }

    @Test
    void buscarLivroPorIdInexistente() {
        Response response = BooksClient.buscarLivroPorId(99999);

        response.then()
                .statusCode(404)
                .body(matchesJsonSchemaInClasspath("schemas/error.json"));
    }

    @Test
    void buscarLivroComIdZero() {
        Response response = BooksClient.buscarLivroPorId(0);

        response.then()
                .statusCode(404)
                .body(matchesJsonSchemaInClasspath("schemas/error.json"));
    }
}
