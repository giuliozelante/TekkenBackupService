package org.gzelante.tbs.config;

public enum OperatingSystems {
    WINDOWS("windows"),
    MAC("mac"),
    LINUX("linux")
    ;

    private final String os;

    OperatingSystems(String os) {
        this.os = os;
    }
}
