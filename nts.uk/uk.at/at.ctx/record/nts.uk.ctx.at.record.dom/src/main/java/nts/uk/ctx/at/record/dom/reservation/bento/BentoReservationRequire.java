package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

public interface BentoReservationRequire {
	
	BentoMenu getBentoMenu(GeneralDate date);
	
	BentoReservation getBefore(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);
	
	void reserve(BentoReservation bentoReservation);
	
	void delete(BentoReservation bentoReservation);
}
