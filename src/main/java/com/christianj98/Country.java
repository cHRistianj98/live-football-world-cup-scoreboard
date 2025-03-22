package com.christianj98;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
    MEXICO("Mexico"),
    CANADA("Canada"),
    SPAIN("Spain"),
    BRAZIL("Brazil"),
    GERMANY("Germany"),
    FRANCE("France"),
    URUGUAY("Uruguay"),
    ITALY("Italy"),
    ARGENTINA("Argentina"),
    AUSTRALIA("Australia");

    private final String name;
}
