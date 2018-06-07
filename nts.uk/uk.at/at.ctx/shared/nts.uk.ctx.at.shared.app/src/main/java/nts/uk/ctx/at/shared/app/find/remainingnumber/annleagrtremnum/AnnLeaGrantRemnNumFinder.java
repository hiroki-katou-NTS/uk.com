package nts.uk.ctx.at.shared.app.find.remainingnumber.annleagrtremnum;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AnnLeaGrantRemnNumFinder {

	@Inject
	private AnnLeaGrantRemDataRepository annLeaDataRepo;
	
	@Inject 
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;

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
		String companyId = AppContexts.user().companyId();
		int retentionYear = annualPaidLeaveSettingRepository.findByCompanyId(companyId).getManageAnnualSetting().getRemainingNumberSetting().retentionYear.v();
		
		GeneralDate expiredDate = grantDate.addYears(retentionYear).addDays(-1);
		
		return expiredDate;
	}
	
	public AnnLeaGrantRemnNumDto getDetail(String id){
		Optional<AnnualLeaveGrantRemainingData> annua = annLeaDataRepo.findByID(id);
		if (annua.isPresent()){
			return AnnLeaGrantRemnNumDto.createFromDomain(annua.get());
		}
		return null;
	}
}
