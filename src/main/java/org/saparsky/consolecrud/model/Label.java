package org.saparsky.consolecrud.model;


import lombok.*;
import org.saparsky.consolecrud.enums.LabelStatus;

@NoArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode
public class Label {
    private Long id;
    private String name;
    private LabelStatus labelStatus;


    public Label(String name, LabelStatus labelStatus) {
        this.name = name;
        this.labelStatus = labelStatus;
    }
}
