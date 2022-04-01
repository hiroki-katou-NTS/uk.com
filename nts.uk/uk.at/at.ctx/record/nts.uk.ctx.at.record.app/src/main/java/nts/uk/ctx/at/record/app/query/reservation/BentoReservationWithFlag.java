package nts.uk.ctx.at.record.app.query.reservation;

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
public class BentoReservationWithFlag {

    public BentoReservationDto bentoReservation;
    
    public boolean canChangeReservation;
}
