package nts.uk.ctx.at.record.pubimp.reservation.bento;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.pub.reservation.bento.BentoMenuExport;
import nts.uk.ctx.at.record.pub.reservation.bento.BentoMenuPub;

@Stateless
public class BentoMenuPubImpl implements BentoMenuPub {

	@Inject
	private BentoMenuRepository bentoMenuRepo;
	
	@Override
	public List<BentoMenuExport> getBentoMenu(String companyID, GeneralDate date) {
		List<Bento> bentoMenus = bentoMenuRepo.getBento(companyID, date);
		
		return bentoMenus.stream()
				.map(b -> BentoMenuExport.builder()
										.amount1(b.getAmount1().v())
										.amount2(b.getAmount2().v())
										.frameNo(b.getFrameNo())
										.name(b.getName().v())
										.reservationTime1Atr(b.isReservationTime1Atr())
										.reservationTime2Atr(b.isReservationTime2Atr())
										.unit(b.getUnit().v())
										.build())
				.collect(Collectors.toList());
	}

}
