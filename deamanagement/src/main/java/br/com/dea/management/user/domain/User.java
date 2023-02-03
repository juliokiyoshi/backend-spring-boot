package br.com.dea.management.user.domain;

import jakarta.persistence.*;
import lombok.*;

import static sun.security.util.Debug.println;

@Entity
@NamedQuery(name = "myQuery", query = "SELECT u FROM User u where u.name = :name")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    /**
     * Reference to customize the getter method:
     * https://stackoverflow.com/questions/18139678/lombok-how-to-customise-getter-for-boolean-object-field
     * or from lombock project: https://projectlombok.org/features/GetterSetter line:
     * You can always manually disable getter/setter generation for any field by using the special AccessLevel.NONE access level.
     * This lets you override the behaviour of a @Getter, @Setter or @Data annotation on a class.
     */
    @Column
    @Getter(AccessLevel.NONE)
    private String linkedin;

    public String getLinkedin() {
        System.out.println("getter customized");
        return linkedin;
    }

}