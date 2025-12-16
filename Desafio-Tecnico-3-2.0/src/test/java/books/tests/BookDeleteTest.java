package books.tests;

import books.client.BooksClient;
import books.utils.BaseTest;
import org.junit.jupiter.api.Test;

public class BookDeleteTest extends BaseTest {

    @Test
    void excluirLivroExistente() {
        BooksClient.excluirLivro(1)
                .then()
                .statusCode(200);
    }


    @Test
    void excluirLivroInexistente() {
        BooksClient.excluirLivro(99999)
                .then()
                .statusCode(404);
    }


    @Test
    void excluirLivroDuasVezes() {
        BooksClient.excluirLivro(1)
                .then()
                .statusCode(200);

        BooksClient.excluirLivro(1)
                .then()
                .statusCode(404);
    }


}
