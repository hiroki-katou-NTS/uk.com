package nts.uk.ctx.at.shared.app.find.remainingnumber.rervleagrtremnum;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoDomService;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;

@Stateless
public class ResvLeaRemainNumberFinder {
	
	@Inject
	private RervLeaGrantRemDataRepository rervLeaDataRepo;
	
	@Inject
	private AnnLeaEmpBasicInfoDomService annLeaDomainService;
	
	public String getResvLeaRemainNumber(String employeeId) {
		List<ReserveLeaveGrantRemainingData> rervLeaveDataList = rervLeaDataRepo.findNotExp(employeeId);
		return annLeaDomainService.calculateRervLeaveNumber(rervLeaveDataList);
	}

}
