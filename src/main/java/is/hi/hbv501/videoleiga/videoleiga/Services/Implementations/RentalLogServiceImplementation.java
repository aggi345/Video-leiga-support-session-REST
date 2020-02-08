package is.hi.hbv501.videoleiga.videoleiga.Services.Implementations;

import is.hi.hbv501.videoleiga.videoleiga.Entities.RentalLog;
import is.hi.hbv501.videoleiga.videoleiga.Repositories.RentalLogRepository;
import is.hi.hbv501.videoleiga.videoleiga.Services.RentalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalLogServiceImplementation implements RentalLogService {
    RentalLogRepository repository;

    @Autowired
    public RentalLogServiceImplementation(RentalLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public RentalLog save(RentalLog rentalLog) {
        return repository.save(rentalLog);
    }

    @Override
    public void delete(RentalLog rentalLog) {
        repository.delete(rentalLog);
    }

    @Override
    public List<RentalLog> findAll() {
        return repository.findAll();
    }
}