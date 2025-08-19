package com.unsamo.unsamo_server;

@Entity
@Table(name = "public_facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point geom;

    // Getter/Setter 생략 가능 (Lombok 쓰면 @Getter @Setter 사용 가능)
}