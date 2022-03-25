package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.reservation.bento.BentoReservationCommand;

@Getter
@Setter
@AllArgsConstructor
public class RegisterNewReservationCommand {
    
    public int frameNo; 
    
    public String correctionDate; 
    
    public List<BentoReservationCommand> bentoReservations;

}
