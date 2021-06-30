package uz.pdp.lesson10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson10.entity.Hotel;
import uz.pdp.lesson10.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @PostMapping()
    public String addHotel(Hotel hotel) {
        boolean exists=hotelRepository.existsByName(hotel.getName());
        if (exists){
            return "This hotel already exists";
        }
        hotelRepository.save(hotel);
        return "Hotel added";
    }

    @GetMapping()
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Integer id) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        return hotelOptional.orElse(null);
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel editingHotel = optionalHotel.get();
            editingHotel.setName(hotel.getName());
            hotelRepository.save(editingHotel);
            return "Successfully edited";
        }
        return "Hotel not found";
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        hotelRepository.deleteById(id);
        boolean deleted = hotelRepository.existsById(id);
        if (deleted) {
            return "Successfully deleted";
        } else {
            return "Hotel not found";
        }
    }
}
