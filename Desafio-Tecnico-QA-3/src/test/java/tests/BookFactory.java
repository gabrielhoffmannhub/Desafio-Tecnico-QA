package tests;

import java.util.HashMap;
import java.util.Map;

public class BookFactory {

    public static Map<String, Object> criarLivroValido() {
        Map<String, Object> body = new HashMap<>();
        body.put("id", 9999);
        body.put("title", "Livro Teste");
        body.put("description", "Descrição teste");
        body.put("pageCount", 123);
        body.put("excerpt", "Trecho teste");
        body.put("publishDate", "2025-01-01T00:00:00");
        return body;
    }

    public static Map<String, Object> criarLivroInvalido() {
        Map<String, Object> body = new HashMap<>();
        body.put("id", "abc");
        return body;
    }
}
