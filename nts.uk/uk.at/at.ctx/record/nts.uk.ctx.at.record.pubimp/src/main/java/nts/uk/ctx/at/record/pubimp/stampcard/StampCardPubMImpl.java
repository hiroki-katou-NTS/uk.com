package nts.uk.ctx.at.record.pubimp.stampcard;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.pub.stampcard.StampCardExport;
import nts.uk.ctx.at.record.pub.stampcard.StampCardPubM;

@Stateless
public class StampCardPubMImpl implements StampCardPubM {

	@Inject
	private StampCardRepository stampCardRepo;
	
	@Override
	public List<StampCardExport> findByEmployees(String contractCode, List<String> empIds) {
		return stampCardRepo.getLstStampCardByLstSidAndContractCd(empIds, contractCode)
				.stream().map(s -> new StampCardExport(s.getContractCd().v(), s.getStampNumber().v(), s.getEmployeeId(), s.getRegisterDate()))
				.collect(Collectors.toList());
	}

}
