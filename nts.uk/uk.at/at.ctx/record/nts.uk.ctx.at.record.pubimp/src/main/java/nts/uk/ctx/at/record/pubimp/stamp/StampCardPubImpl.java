package nts.uk.ctx.at.record.pubimp.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.pub.stamp.StampCardExport;
import nts.uk.ctx.at.record.pub.stamp.StampCardPub;

@Stateless
public class StampCardPubImpl implements StampCardPub {

	@Inject
	private StampCardRepository repo;

	@Override
	public Optional<StampCardExport> getByCardNoAndContractCode(String contractCode, String stampNumber) {

		return repo.getByCardNoAndContractCode(stampNumber, contractCode)
				.map(x -> new StampCardExport(x.getStampCardId(), x.getEmployeeId()));
	}

}
