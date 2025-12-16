package books.tests;

import books.client.BooksClient;
import books.factory.BookFactory;
import books.utils.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class BooksCreateTest extends BaseTest {

    @Test
    void criarLivroValido() {
        Response response = BooksClient.criarLivro(BookFactory.validarBook());

        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/book.json"))
                .body("id", notNullValue());
    }

    @Test
    void criarLivroComIdJaExistente() {
        Response primeiro = BooksClient.criarLivro(BookFactory.validarBook());
        int idExistente = primeiro.then().extract().path("id");

        Response response = BooksClient.criarLivro(
                BookFactory.bookComIdExistente(idExistente)
        );

        response.then()
                .statusCode(400);
    }

    @Test
    void criarLivroComCampoObrigatorioAusente() {
        Response response = BooksClient.criarLivro(
                BookFactory.bookComCampoObrigatorioAusente()
        );

        response.then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/error.json"));
    }

    @Test
    void criarLivroComTipoDeDadoInvalido() {
        Response response = BooksClient.criarLivro(
                BookFactory.bookComPayloadInvalido()
        );

        response.then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/error.json"));
    }

    @Test
    void criarLivroComCamposVazios() {
        Response response = BooksClient.criarLivro(
                BookFactory.bookComCamposVazios()
        );

        response.then()
                .statusCode(400);
    }

    @Test
    void criarLivroComCamposMuitoLongos() {
        Response response = BooksClient.criarLivro(
                BookFactory.bookComCamposMuitoLongos()
        );

        response.then()
                .statusCode(400);
    }

    @Test
    void criarLivroComDataInvalida() {
        Response response = BooksClient.criarLivro(
                BookFactory.bookComDataInvalida()
        );

        response.then()
                .statusCode(400);
    }

    @Test
    void criarLivroComCampoExtraNaoDocumentado() {
        Response response = BooksClient.criarLivro(
                BookFactory.bookComCampoExtra()
        );

        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/book.json"));
    }
}
