package nts.uk.ctx.pr.core.app.find.rule.employment.allot;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingRepository;

@Stateless
public class EmployeeAllotSettingFinder{
	@Inject
	private EmployeeAllotSettingRepository EmpAllotSettingRepo;
	/**
	 * 
	 * @param companyCode
	 * @param historyId
	 * @return
	 */
	public List<EmployeeAllotSettingDto> getAllEmployeeAllotDetailSetting(String companyCode,String historyId) {
		return this.EmpAllotSettingRepo.findAll(companyCode, historyId).stream().map(m -> EmployeeAllotSettingDto.fromDomain(m))
				.collect(Collectors.toList());
	}
	
	public List<getEmploymentListDto> getAllEmployeeAllotSettingList(String companyCode,String historyID, String employeeCode){
		return this.EmpAllotSettingRepo.findAllEm(companyCode, historyID, employeeCode).stream().map(e -> getEmploymentListDto.fromDomain(e))
				.collect(Collectors.toList());
	}
}
