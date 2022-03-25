package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class BentoReservationWithEmp {

    public BentoReservation bentorReservation;
    
    public String employeeCode;
    
    public String employeeName;
}
