package mk.ukim.finki.wpaud.service.impl;

import mk.ukim.finki.wpaud.model.Manufacturer;
import mk.ukim.finki.wpaud.repository.InMemoryManufacturerRepository;
import mk.ukim.finki.wpaud.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    //Постојат повеќе начини на инјектирање, една од нив е @Autowired, но IntelliJ ќе рече не е recommended.
    private final InMemoryManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(InMemoryManufacturerRepository inMemoryManufacturerRepository) {
        this.manufacturerRepository = inMemoryManufacturerRepository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return this.manufacturerRepository.findById(id);
    }

    @Override
    public Optional<Manufacturer> save(String name, String address) {
        return this.manufacturerRepository.save(name, address);
    }

    @Override
    public boolean deleteById(Long id) {
        return this.manufacturerRepository.deleteById(id);
    }
}
