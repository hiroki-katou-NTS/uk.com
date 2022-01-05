package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class BentoReservationWithEmpCommand {

    public BentoReservationCommand bentorReservation;
    
    public String employeeCode;
    
    public String employeeName;
    
    public BentoReservationWithEmp toDomain() {
        return new BentoReservationWithEmp(
                bentorReservation.toDomain(), 
                employeeCode, 
                employeeName);
    }
}
