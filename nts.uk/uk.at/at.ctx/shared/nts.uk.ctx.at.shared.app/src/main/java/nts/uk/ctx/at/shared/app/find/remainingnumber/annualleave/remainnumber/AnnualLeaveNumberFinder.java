package nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave.remainnumber;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoDomService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AnnualLeaveNumberFinder {
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaDataRepo;
	
	@Inject
	private AnnLeaEmpBasicInfoDomService annLeaDomainService;
	
	public RemainNumberInfoDto getAnnualLeaveNumber(String employeeId) {
		String companyId = AppContexts.user().companyId();
		
		// get data
		List<AnnualLeaveGrantRemainingData> annualLeaveDataList = annLeaDataRepo.findNotExp(employeeId);
		
		// compute result with data
		RemainNumberInfoDto dto = new RemainNumberInfoDto();
		dto.setAnnualLeaveNumber(annLeaDomainService.calculateAnnLeaNumWithFormat(companyId, annualLeaveDataList));
		dto.setLastGrantDate(annLeaDomainService.calculateLastGrantDate(employeeId));
		return dto;
	}

}
