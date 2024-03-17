package uz.ilmnajot.elibrary.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.ilmnajot.elibrary.base.BaseEntity;
import uz.ilmnajot.elibrary.enums.RoleName;


@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private boolean deleted;
}
