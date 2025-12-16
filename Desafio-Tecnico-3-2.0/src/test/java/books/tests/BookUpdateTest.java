package books.tests;

import books.client.BooksClient;
import books.factory.BookFactory;
import books.utils.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class BookUpdateTest extends BaseTest {

    @Test
    void atualizarLivroValido() {
        int bookId = BooksClient
                .criarLivro(BookFactory.validarBook())
                .then()
                .extract()
                .path("id");


        Response response = BooksClient.atualizarLivro(
                bookId,
                BookFactory.validarBookAtualizado(bookId)
        );

        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/book.json"))
                .body("id", equalTo(bookId));
    }

    @Test
    void atualizarLivroInexistente() {
        Response response = BooksClient.atualizarLivro(
                99999,
                BookFactory.validarBookAtualizado(99999)
        );

        response.then()
                .statusCode(404);
    }

    @Test
    void atualizarLivroComPayloadInvalido() {
        int bookId = BooksClient
                .criarLivro(BookFactory.validarBook())
                .then()
                .extract()
                .path("id");


        Response response = BooksClient.atualizarLivro(
                bookId,
                BookFactory.bookComPayloadInvalido()
        );

        response.then()
                .statusCode(400);
    }

    @Test
    void atualizarLivroComIdDiferenteNoBody() {
        int bookId = BooksClient
                .criarLivro(BookFactory.validarBook())
                .then()
                .extract()
                .path("id");


        Response response = BooksClient.atualizarLivro(
                bookId,
                BookFactory.validarBookAtualizado(bookId + 1)
        );

        response.then()
                .statusCode(400);
    }
}
