package ru.buttonone.dto;

public interface TodoAppDto {

    String todoAppToDto(String idExpected, String textExpected, boolean completedExpected);

    String todoAppToDtoWithoutCompleted(String idExpected, String textExpected, boolean completedExpected);

    String todoAppToDtoWithoutText(String idExpected, String textExpected, boolean completedExpected);

    String todoAppToDtoWithoutId(String idExpected, String textExpected, boolean completedExpected);
}