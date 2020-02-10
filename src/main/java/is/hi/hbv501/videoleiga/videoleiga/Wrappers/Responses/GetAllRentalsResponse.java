package is.hi.hbv501.videoleiga.videoleiga.Wrappers.Responses;

import is.hi.hbv501.videoleiga.videoleiga.Entities.RentalLog;

import java.util.List;

/************************
 * Höfundur: Kristján P.*
 ************************/
public class GetAllRentalsResponse extends GenericResponse {

    private List<RentalLog> rentals;

    public GetAllRentalsResponse(List<RentalLog> rentals){
        this.rentals = rentals;
    }

    public GetAllRentalsResponse(List<RentalLog> rentals, String message, List<?> errors) {
        super(message, errors);
        this.rentals = rentals;
    }

    public List<RentalLog> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalLog> rentals) {
        this.rentals = rentals;
    }
}
