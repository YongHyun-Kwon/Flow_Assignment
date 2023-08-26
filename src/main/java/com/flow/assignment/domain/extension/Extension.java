package com.flow.assignment.domain.extension;

import com.flow.assignment.web.dto.ExtensionRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "extension")
public class Extension {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extension_id")
    private Long id;
    @Column(name = "extension_name", unique = true)
    @Length(max = 20)
    private String name;
    @Enumerated(EnumType.STRING)
    private ExtensionType type;
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isChecked;

    @Builder
    public Extension(String name, ExtensionType type, boolean isChecked) {
        this.name = name;
        this.type = type;
        this.isChecked = isChecked;
    }

    public static Extension createExtension(ExtensionRequestDto requestDto) {
        return Extension.builder()
                .name(requestDto.getName())
                .type(ExtensionType.CUSTOM)
                .build();
    }

    public void checkedExtension(boolean isChecked) {
        this.isChecked = isChecked;
    }

}

