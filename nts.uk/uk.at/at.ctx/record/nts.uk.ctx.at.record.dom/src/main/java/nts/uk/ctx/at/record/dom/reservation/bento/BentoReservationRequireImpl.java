package nts.uk.ctx.at.record.dom.reservation.bento;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReservationRequireImpl implements BentoReservationRequire {

	@Inject
	private BentoMenuRepository bentoMenuRepository;
	
	@Inject
	private BentoReservationRepository bentoReservationRepository;
	
	@Override
	public BentoMenu getBentoMenu(GeneralDate date) {
		return bentoMenuRepository.getBentoMenu(date);
	}

	@Override
	public BentoReservation getBefore(ReservationRegisterInfo registerInfor, ReservationDate reservationDate) {
		return bentoReservationRepository.getBefore(registerInfor, reservationDate);
	}

	@Override
	public void reserve(BentoReservation bentoReservation) {
		bentoReservationRepository.add(bentoReservation);
	}

	@Override
	public void delete(BentoReservation bentoReservation) {
		bentoReservationRepository.delete(bentoReservation);
	}
	
	
	
}
