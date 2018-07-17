package nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.generalinfo.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.ExClassificationHistoryImport;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ResponseImprovementDefault implements ResponseImprovementService{

	@Inject
	private EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter;
	
	@Inject
	private BusinessTypeOfEmployeeRepository businessTypeOfEmployeeRepo;
	
	@Override
	public List<String> reduceTargetResponseImprovement(List<String> employeeIds, DatePeriod period,
			AlarmCheckTargetConRc alarmCheckTargetConRc) {
		List<String> listEmployeeIdOutput = new ArrayList<>();
		//Imported「社員の履歴情報」を取得する
		EmployeeGeneralInfoImport employeeGeneralInfoImport = employeeGeneralInfoAdapter.getEmployeeGeneralInfo(
				employeeIds, period, 
				alarmCheckTargetConRc.isFilterByEmployment(),
				alarmCheckTargetConRc.isFilterByClassification(),
				alarmCheckTargetConRc.isFilterByJobTitle(),
				false,
				false);
		//「社員の勤務種別」を取得する
		List<String> listBusinessTypeOfEmployee = new ArrayList<>(); 
		
		//employee bussiness
		if(alarmCheckTargetConRc.isFilterByBusinessType()) {
			listBusinessTypeOfEmployee = businessTypeOfEmployeeRepo.findAllByListCode(employeeIds)
					.stream().map(c->c.getSId()).collect(Collectors.toList());
		}
		if(alarmCheckTargetConRc.isFilterByBusinessType()) {
			for(String employeeBusiness : listBusinessTypeOfEmployee){
				for(String empBusiness : alarmCheckTargetConRc.getLstBusinessTypeCode()){
					if(employeeBusiness.equals(empBusiness)) {
						listEmployeeIdOutput.add(employeeBusiness);
					}
				}
			}
		}
		//employee classification
		if(alarmCheckTargetConRc.isFilterByClassification()) {
			List<String> listEmpClass = employeeGeneralInfoImport.getExClassificationHistoryImports().stream()
					.map(c->c.getEmployeeId()).collect(Collectors.toList());
			for(String employeeclass : listEmpClass){
				for(String empClass : alarmCheckTargetConRc.getLstClassificationCode()){
					if(employeeclass.equals(empClass)) {
						listEmployeeIdOutput.add(employeeclass);
					}
				}
			}
		}
		//employee employment
		if(alarmCheckTargetConRc.isFilterByEmployment()) {
			List<String> listEmpEmployment = employeeGeneralInfoImport.getEmploymentHistoryImports().stream()
					.map(c->c.getEmployeeId()).collect(Collectors.toList());
			for(String employeeEmployment : listEmpEmployment){
				for(String empEmployment : alarmCheckTargetConRc.getLstClassificationCode()){
					if(employeeEmployment.equals(empEmployment)) {
						listEmployeeIdOutput.add(employeeEmployment);
					}
				}
			}
		}
		//employee JobTitle
		if(alarmCheckTargetConRc.isFilterByJobTitle()) {
			List<String> listEmpJobTitle = employeeGeneralInfoImport.getExJobTitleHistoryImports().stream()
					.map(c->c.getEmployeeId()).collect(Collectors.toList());
			for(String employeeJobTitle : listEmpJobTitle){
				for(String empJobTitle : alarmCheckTargetConRc.getLstJobTitleId()){
					if(employeeJobTitle.equals(empJobTitle)) {
						listEmployeeIdOutput.add(employeeJobTitle);
					}
				}
			}
		}
		return listEmployeeIdOutput;
	}
	

}
