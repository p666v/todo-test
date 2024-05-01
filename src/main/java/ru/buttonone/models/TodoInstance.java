package ru.buttonone.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("myFilter")
public class TodoInstance {
    private BigInteger id;
    private String text;
    private boolean completed;
}