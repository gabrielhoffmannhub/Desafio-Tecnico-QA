package books.model;

public class BookRequest {

    private Object id;
    private Object title;
    private Object description;
    private Object pageCount;
    private Object publishDate;

    public BookRequest(Object id, Object title, Object description, Object pageCount, Object publishDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.publishDate = publishDate;
    }

    public Object getId() {
        return id;
    }

    public Object getTitle() {
        return title;
    }

    public Object getDescription() {
        return description;
    }

    public Object getPageCount() {
        return pageCount;
    }

    public Object getPublishDate() {
        return publishDate;
    }
}
