package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Optional;

public interface BentoReservationRepository {
	
	public Optional<BentoReservation> find(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);
	
	public void add(BentoReservation bentoReservation);
	
	public void delete(BentoReservation bentoReservation);
	
}
