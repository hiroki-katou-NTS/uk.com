package nts.uk.ctx.at.function.ac.reservation.bento;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.reservation.bento.BentoMenuAdaptor;
import nts.uk.ctx.at.function.dom.adapter.reservation.bento.BentoMenuImport;
import nts.uk.ctx.at.record.pub.reservation.bento.BentoMenuPub;

@Stateless
public class BentoMenuAdaptorImpl implements BentoMenuAdaptor {

	@Inject
	private BentoMenuPub bentoMenuPub;
	
	@Override
	public List<BentoMenuImport> getBentoMenu(String companyID, GeneralDate date) {
		return bentoMenuPub.getBentoMenu(companyID, date).stream()
				.map(b -> BentoMenuImport.builder()
										.amount1(b.getAmount1())
										.amount2(b.getAmount2())
										.frameNo(b.getFrameNo())
										.name(b.getName())
										.reservationTime1Atr(b.isReservationTime1Atr())
										.reservationTime2Atr(b.isReservationTime2Atr())
										.unit(b.getUnit())
										.build())
				.collect(Collectors.toList());
	}

}
