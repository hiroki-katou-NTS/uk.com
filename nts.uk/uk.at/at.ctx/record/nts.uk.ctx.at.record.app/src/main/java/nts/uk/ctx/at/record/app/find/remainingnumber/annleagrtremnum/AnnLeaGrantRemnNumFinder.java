package nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
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
	
	public List<AnnLeaGrantRemnNumDto> getListDataByCheckState(String employeeId, Boolean checkState) {
		List<AnnualLeaveGrantRemainingData> annLeaDataList = annLeaDataRepo.find(employeeId);
		return annLeaDataList.stream().map(domain -> AnnLeaGrantRemnNumDto.createFromDomain(domain))
				.collect(Collectors.toList());
	}

	public GeneralDate lostFocus(GeneralDate grantDate){
		// 保持年数を取得
		int retentionYear  = 0;
		GeneralDate expiredDate = grantDate.addYears(retentionYear).addDays(-1);
		
		return expiredDate;
	}
}
