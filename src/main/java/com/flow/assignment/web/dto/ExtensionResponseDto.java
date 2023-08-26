package com.flow.assignment.web.dto;

import com.flow.assignment.domain.extension.Extension;
import com.flow.assignment.domain.extension.ExtensionType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExtensionResponseDto {

    private Long id;
    private String name;
    private ExtensionType type;
    private Boolean isChecked;

    public ExtensionResponseDto(Extension extension) {
        this.id = extension.getId();
        this.name = extension.getName();
        this.type = extension.getType();
        this.isChecked = extension.isChecked();
    }

}
