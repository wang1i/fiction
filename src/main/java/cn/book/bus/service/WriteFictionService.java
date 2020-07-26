package cn.book.bus.service;


import cn.book.bus.domain.Fiction;
import org.jsoup.nodes.Document;

public interface WriteFictionService {

    void insert(String fictionURL);

    Fiction getFictions(Document document);

}
