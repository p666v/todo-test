package ru.buttonone.dto;

public interface TodoAppDto {

    String todoAppToDto(String id, String text, boolean completed);

    String todoAppToDtoWithoutCompleted(String id, String text);

    String todoAppToDtoWithoutText(String id, boolean completed);

    String todoAppToDtoWithoutId(String text, boolean completed);

    String todoAppToDtoEmpty();
}