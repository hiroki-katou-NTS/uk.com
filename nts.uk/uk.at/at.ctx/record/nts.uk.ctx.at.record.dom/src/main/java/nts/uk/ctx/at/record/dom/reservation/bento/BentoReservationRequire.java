package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

public interface BentoReservationRequire {
	
	BentoMenu getBentoMenu(ReservationDate reservationDate);
	
	Optional<BentoReservation> getBefore(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);
	
	void reserve(BentoReservation bentoReservation);
	
	void delete(BentoReservation bentoReservation);
	
}
