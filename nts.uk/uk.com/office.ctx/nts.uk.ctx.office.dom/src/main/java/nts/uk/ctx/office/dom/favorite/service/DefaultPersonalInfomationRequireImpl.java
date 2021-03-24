package nts.uk.ctx.office.dom.favorite.service;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeePositionAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeWorkplaceIdAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.PersonalInformationAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.RankOfPositionAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.SequenceMasterImport;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforImport;

@AllArgsConstructor
public class DefaultPersonalInfomationRequireImpl implements PersonalInfomationDomainService.Require {

	private EmployeeWorkplaceIdAdapter employeeWorkplaceIdAdapter;
	
	private WorkplaceInforAdapter workplaceInforAdapter;
	
	private EmployeePositionAdapter employeePositionAdapter;
	
	private RankOfPositionAdapter rankOfPositionAdapter;
	
	private PersonalInformationAdapter personalInformationAdapter;
	
	@Override
	public Map<String, String> getEmployeesWorkplaceId(List<String> sIds, GeneralDate baseDate) {
		return employeeWorkplaceIdAdapter.getWorkplaceId(sIds, baseDate);
	}

	@Override
	public Map<String, WorkplaceInforImport> getWorkplaceInfor(List<String> lstWorkplaceID, GeneralDate baseDate) {
		return workplaceInforAdapter.getWorkplaceInfor(lstWorkplaceID, baseDate);
	}

	@Override
	public Map<String, EmployeeJobHistImport> getPositionBySidsAndBaseDate(List<String> sIds, GeneralDate baseDate) {
		return employeePositionAdapter.getPositionBySidsAndBaseDate(sIds, baseDate);
	}

	@Override
	public List<SequenceMasterImport> getRankOfPosition() {
		return rankOfPositionAdapter.getRankOfPosition();
	}

	@Override
	public Map<String, EmployeeBasicImport> getPersonalInformation(List<String> lstSid) {
		return personalInformationAdapter.getPersonalInformation(lstSid);
	}
}
