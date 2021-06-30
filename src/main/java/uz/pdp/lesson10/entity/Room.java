package uz.pdp.lesson10.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"floor","number"}))
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer number;


    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String size;


    @ManyToOne
    private Hotel hotel;
}
