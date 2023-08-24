package com.flow.assignment.web.dto;

import com.flow.assignment.domain.extension.CustomExtension;
import com.flow.assignment.domain.extension.Extension;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ExtensionRequestDto {

    private String name;
    private boolean isChecked;
    private boolean isDeleted;

    @Builder
    public ExtensionRequestDto(String name, boolean isChecked, boolean isDeleted) {
        this.name = name;
        this.isChecked = isChecked;
        this.isDeleted = isDeleted;
    }

    public Extension toExtension() {
        return Extension.builder()
                .name(name)
                .isChecked(isChecked)
                .build();
    }

    public CustomExtension toCustomExtension() {
        return CustomExtension.builder()
                .name(name)
                .isDeleted(isDeleted)
                .build();
    }
}
