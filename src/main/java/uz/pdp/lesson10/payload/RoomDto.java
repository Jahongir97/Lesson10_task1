package uz.pdp.lesson10.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDto {
    private Integer number;
    private Integer floor;
    private String  size;
    private Integer hotelId;

}
