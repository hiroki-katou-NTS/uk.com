package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;

public interface BentoReservationRepository {
	
	public Optional<BentoReservation> find(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);
	
	public List<BentoReservation> findList(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);
	
	public void add(BentoReservation bentoReservation);
	
	public void delete(BentoReservation bentoReservation);
	
	public void update(BentoReservation bentoReservation);

	public List<BentoReservation> findByOrderedPeriodEmpLst(List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate,
															boolean ordered,Optional<WorkLocationCode> workLocationCode);

    public List<BentoReservation> getFromReservationTargetDate(List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate,
                                                               Optional<WorkLocationCode> workLocationCode);

    public List<BentoReservation> acquireReservationDetails (List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate,
                                                               Optional<WorkLocationCode> workLocationCode);

    public List<BentoReservation> getEmployeeNotOrder (List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate);

}
