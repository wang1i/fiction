package cn.book.bus.service;

import javax.servlet.http.HttpServletResponse;

public interface DownloadService {

    void download(int fiction_id, String name, HttpServletResponse response);
}
