package nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoDomService;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ResvLeaRemainNumberFinder {
	
	@Inject
	private RervLeaGrantRemDataRepository rervLeaDataRepo;
	
	@Inject
	private AnnLeaEmpBasicInfoDomService annLeaDomainService;
	
	public String getResvLeaRemainNumber(String employeeId) {
		String companyId = AppContexts.user().companyId();
		List<ReserveLeaveGrantRemainingData> rervLeaveDataList = rervLeaDataRepo.findNotExp(employeeId, companyId);
		return annLeaDomainService.calculateRervLeaveNumber(rervLeaveDataList);
	}

}
