package nts.uk.ctx.at.record.dom.reservation.bento;

public interface BentoReservationRepository {
	
	public BentoReservation getBefore(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);
	
	public void add(BentoReservation bentoReservation);
	
	public void delete(BentoReservation bentoReservation);
	
}
