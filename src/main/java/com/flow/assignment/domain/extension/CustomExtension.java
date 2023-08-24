package com.flow.assignment.domain.extension;

import com.flow.assignment.domain.BaseTime;
import com.flow.assignment.web.dto.CustomExtensionDto;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE custom_extension SET is_deleted = true WHERE custom_extension_id = ?")
@Where(clause = "is_deleted = false")
public class CustomExtension extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_extension_id")
    private Long id;

    @Length(max = 20)
    @Column(name = "custom_extension_name")
    @NotNull
    private String name;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isDeleted;

    @Builder
    public CustomExtension(String name, boolean isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public static CustomExtension createCustomException(ExtensionRequestDto extensionRequestDto) {
        return CustomExtension.builder()
                .name(extensionRequestDto.getName())
                .isDeleted(extensionRequestDto.isDeleted())
                .build();
    }

    public void updateExtension(ExtensionRequestDto extensionRequestDto) {
        this.name = extensionRequestDto.getName();
    }

}
