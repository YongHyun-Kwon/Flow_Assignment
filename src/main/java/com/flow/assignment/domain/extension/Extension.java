package com.flow.assignment.domain.extension;

import com.flow.assignment.web.dto.ExtensionRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long id; // 확장자 Id
    @Column(name = "extension_name", unique = true)
    @Length(max = 20)
    private String name; // 확장자 명
    @Enumerated(EnumType.STRING)
    private ExtensionType type; // 확장자 타입
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isChecked; // 확장자 체크 여부

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

