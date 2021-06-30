package uz.pdp.lesson10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson10.entity.Hotel;
import uz.pdp.lesson10.entity.Room;
import uz.pdp.lesson10.payload.RoomDto;
import uz.pdp.lesson10.repository.HotelRepository;
import uz.pdp.lesson10.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    @PostMapping()
    public String addRoom(@RequestBody RoomDto roomDto) {
        boolean exists = roomRepository.existsByFloorAndNumber(roomDto.getFloor(), roomDto.getNumber());
        if (exists) {
            return "This room already exists";
        } else {
            Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
            Room room = new Room();
            room.setFloor(roomDto.getFloor());
            room.setNumber(roomDto.getNumber());
            room.setSize(roomDto.getSize());
            optionalHotel.ifPresent(room::setHotel);
            return "Room added";
        }
    }

    @GetMapping()
    public Page<Room> getRooms(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAll(pageable);
    }

    @GetMapping("/{hotelId}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer hotelId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAllByHotelId(hotelId, pageable);
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalRoom.isPresent()) {
            Room editingRoom = optionalRoom.get();
            Hotel hotel = editingRoom.getHotel();
            editingRoom.setFloor(roomDto.getFloor());
            editingRoom.setNumber(roomDto.getNumber());
            editingRoom.setSize(roomDto.getSize());
            optionalHotel.ifPresent(editingRoom::setHotel);
            hotelRepository.save(hotel);
            roomRepository.save(editingRoom);
            return "Successfully edited";
        }
        return "Room not found";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        roomRepository.deleteById(id);
        boolean deleted = roomRepository.existsById(id);
        if (deleted) {
            return "Successfully deleted";
        } else {
            return "Room not found";
        }
    }

}
