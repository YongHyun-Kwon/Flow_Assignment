package com.flow.assignment.web.dto;

import com.flow.assignment.domain.extension.Extension;
import com.flow.assignment.domain.extension.ExtensionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor
public class ExtensionRequestDto {

    @Length(max = 20, message = "20자 이하만 가능합니다.")
    @NotBlank(message = "공백은 불가능합니다.")
    private String name;
    private ExtensionType type;

    @Builder
    public ExtensionRequestDto(String name, ExtensionType type) {
        this.name = name;
        this.type = type;
    }

    public Extension toExtension() {
        return Extension.builder()
                .name(name)
                .type(type)
                .build();
    }

}
