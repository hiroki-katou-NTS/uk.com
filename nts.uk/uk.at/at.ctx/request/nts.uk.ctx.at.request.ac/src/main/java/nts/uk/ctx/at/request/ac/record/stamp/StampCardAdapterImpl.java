package nts.uk.ctx.at.request.ac.record.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.stamp.StampCardPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.stamp.StampCardAdapter;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.StampCard;

@Stateless
public class StampCardAdapterImpl implements StampCardAdapter {

	@Inject
	private StampCardPub pub;

	@Override
	public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber) {
		return pub.getByCardNoAndContractCode(contractCode, stampNumber)
				.map(x -> new StampCard(x.getStampCardId(), x.getEmployeeId()));
	}

}
