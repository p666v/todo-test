package ru.buttonone;

import ru.buttonone.services.UploadingTestDataService;
import ru.buttonone.services.UploadingTestDataServiceImpl;

public class Main {
    public static void main(String[] args) {
        UploadingTestDataService service = new UploadingTestDataServiceImpl();
        service.uploadData();
    }
}