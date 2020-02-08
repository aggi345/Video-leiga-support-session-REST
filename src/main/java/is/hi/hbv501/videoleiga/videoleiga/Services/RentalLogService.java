package is.hi.hbv501.videoleiga.videoleiga.Services;

import is.hi.hbv501.videoleiga.videoleiga.Entities.RentalLog;

import java.util.List;

public interface RentalLogService {
    RentalLog save(RentalLog rentalLog);
    void delete(RentalLog rentalLog);
    List<RentalLog> findAll();
}