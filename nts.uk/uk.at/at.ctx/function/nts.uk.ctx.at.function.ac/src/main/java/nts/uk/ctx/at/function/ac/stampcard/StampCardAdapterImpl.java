package nts.uk.ctx.at.function.ac.stampcard;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.stampcard.StampCardAdapter;
import nts.uk.ctx.at.function.dom.adapter.stampcard.StampCardImport;
import nts.uk.ctx.at.record.pub.stampcard.StampCardPub;

@Stateless
public class StampCardAdapterImpl implements StampCardAdapter {
	
	@Inject
	private StampCardPub stampCardPub; 

	@Override
	public List<StampCardImport> findByEmployees(String contractCode, List<String> empIds) {
		return stampCardPub.findByEmployees(contractCode, empIds)
				.stream().map(s -> new StampCardImport(s.getContractCd(), s.getStampNumber(), s.getEmployeeId(), s.getRegisterDate()))
				.collect(Collectors.toList());
	}
}
