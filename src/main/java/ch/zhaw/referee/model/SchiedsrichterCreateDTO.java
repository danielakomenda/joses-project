package ch.zhaw.referee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SchiedsrichterCreateDTO {
    private String name;
    private String email;
    private int level;
}