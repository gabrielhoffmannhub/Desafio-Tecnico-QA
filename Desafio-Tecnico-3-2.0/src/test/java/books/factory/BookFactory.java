package books.factory;

import books.model.BookRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BookFactory {

    public static BookRequest validarBook() {
        return new BookRequest(
                0,
                "Clean Code",
                "Robert Martin",
                100,
                LocalDate.now().toString()
        );
    }
    public static BookRequest validarBookAtualizado(int id) {
        return new BookRequest(
                id,
                "Clean Code - Edição Atualizada",
                "Robert Martin",
                150,
                LocalDate.now().toString()
        );
    }


    public static BookRequest bookComIdExistente(int id) {
        return new BookRequest(
                id,
                "Livro Duplicado",
                "Autor Teste",
                120,
                LocalDate.now().toString()
        );
    }

    public static BookRequest bookComCampoObrigatorioAusente() {
        return new BookRequest(
                0,
                null,
                "Autor Teste",
                120,
                LocalDate.now().toString()
        );
    }

    public static BookRequest bookComPayloadInvalido() {
        return new BookRequest(
                "id",
                123,
                true,
                "cem",
                "data-invalida"
        );
    }

    public static BookRequest bookComCamposVazios() {
        return new BookRequest(
                0,
                "",
                "",
                0,
                ""
        );
    }

    public static BookRequest bookComCamposMuitoLongos() {
        String textoLongo = "A".repeat(1000);

        return new BookRequest(
                0,
                textoLongo,
                textoLongo,
                999999999,
                LocalDate.now().toString()
        );
    }


    public static BookRequest bookComDataInvalida() {
        return new BookRequest(
                0,
                "Livro com Data Errada",
                "Autor Teste",
                150,
                "32-13-2024"
        );
    }

    public static Map<String, Object> bookComCampoExtra() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 0);
        payload.put("title", "Livro com Campo Extra");
        payload.put("description", "Autor Teste");
        payload.put("pageCount", 200);
        payload.put("publishDate", LocalDate.now().toString());
        payload.put("campoExtra", "valorNaoDocumentado");
        return payload;
    }
}
