package main.java.com.nir.shopping.tracker.domain;

@Entity
@Table(name = "set_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String hashPassword;

    // Getters and setters
}