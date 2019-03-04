package fr.ecp.is1220.tutorial5.ex2;

import java.io.IOException;

public class BadFileException extends IOException {
    public BadFileException(String e) {
        super(e);
    }
}
