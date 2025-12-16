package books.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class BooksClient {

    private static final String BASE_PATH = "/Books";

    public static Response criarLivro(Object payload) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(BASE_PATH);
    }

    public static Response buscarTodosLivros() {
        return given()
                .when()
                .get(BASE_PATH);
    }

    public static Response buscarLivroPorId(int id) {
        return given()
                .when()
                .get(BASE_PATH + "/" + id);
    }

    public static Response atualizarLivro(int id, Object payload) {
        return given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(BASE_PATH + "/" + id);
    }

    public static Response excluirLivro(int id) {
        return given()
                .when()
                .delete(BASE_PATH + "/" + id);
    }
}
