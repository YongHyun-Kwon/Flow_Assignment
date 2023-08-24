package com.flow.assignment.domain.extension;

import com.flow.assignment.domain.BaseTime;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Extension extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extension_id")
    private Long id;

    @Column(name = "extension_name", unique = true)
    @NotNull
    private String name;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isChecked;

    @Builder
    public Extension(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public void checkedExtension(ExtensionRequestDto extensionDto) {
        this.isChecked = extensionDto.isChecked();
    }
}
