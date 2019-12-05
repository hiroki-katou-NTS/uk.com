package nts.uk.ctx.at.record.dom.reservation.bento;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReservationRequireImpl implements BentoReservationRequire {

	@Inject
	private BentoMenuRepository bentoMenuRepository;
	
	@Inject
	private BentoReservationRepository bentoReservationRepository;
	
	@Override
	public BentoMenu getBentoMenu(ReservationDate reservationDate) {
		String companyID = AppContexts.user().companyId();
		return bentoMenuRepository.getBentoMenu(companyID, reservationDate.getDate());
	}

	@Override
	public BentoReservation getBefore(ReservationRegisterInfo registerInfor, ReservationDate reservationDate) {
		return bentoReservationRepository.find(registerInfor, reservationDate).orElseGet(() -> {
			throw new BusinessException(new RawErrorMessage("System Error"));
		});
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
