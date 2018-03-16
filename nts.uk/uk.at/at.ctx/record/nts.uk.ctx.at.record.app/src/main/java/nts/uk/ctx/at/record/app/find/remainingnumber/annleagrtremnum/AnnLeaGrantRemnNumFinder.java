package nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;

@Stateless
public class AnnLeaGrantRemnNumFinder {

	@Inject
	private AnnLeaGrantRemDataRepository annLeaDataRepo;

	public List<AnnLeaGrantRemnNumDto> getListData(String employeeId) {
		List<AnnualLeaveGrantRemainingData> annLeaDataList = annLeaDataRepo.find(employeeId);
		return annLeaDataList.stream().map(domain -> AnnLeaGrantRemnNumDto.createFromDomain(domain))
				.collect(Collectors.toList());
	}

}
