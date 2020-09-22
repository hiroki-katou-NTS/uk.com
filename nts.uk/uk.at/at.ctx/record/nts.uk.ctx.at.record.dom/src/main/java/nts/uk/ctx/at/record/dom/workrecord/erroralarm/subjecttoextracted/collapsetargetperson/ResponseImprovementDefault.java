package nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.generalinfo.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExClassificationHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExJobTitleHistoryImport;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class ResponseImprovementDefault implements ResponseImprovementService{

	@Inject
	private EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter;
	
	@Inject
	private BusinessTypeOfEmployeeRepository businessTypeOfEmployeeRepo;
	
	@Override
	public List<String> reduceTargetResponseImprovement(List<String> employeeIds, DatePeriod period,
			AlarmCheckTargetConRc alarmCheckTargetConRc) {
		List<String> listBusinessTypeOutput = new ArrayList<>();
		List<String> listClassificationCodeOutput = new ArrayList<>();
		List<String> listEmploymentOutput = new ArrayList<>();
		List<String> listJobTitleOutput = new ArrayList<>();
		//Imported「社員の履歴情報」を取得する
		EmployeeGeneralInfoImport employeeGeneralInfoImport = employeeGeneralInfoAdapter.getEmployeeGeneralInfo(
				employeeIds, new DatePeriod(period.end(), period.end()), 
				alarmCheckTargetConRc.isFilterByEmployment(),
				alarmCheckTargetConRc.isFilterByClassification(),
				alarmCheckTargetConRc.isFilterByJobTitle(),
				false,
				false);
		//「社員の勤務種別」を取得する
		List<BusinessTypeOfEmployee> listBusinessTypeOfEmployee = new ArrayList<>(); 
		
		List<List<String>> listFilterOutput = new ArrayList<List<String>>();
		
		//employee bussiness
		if(alarmCheckTargetConRc.isFilterByBusinessType()) {
			listBusinessTypeOfEmployee = businessTypeOfEmployeeRepo.findAllByEmpAndDate(employeeIds, period)
					.stream().collect(Collectors.toList());
		}
		if(alarmCheckTargetConRc.isFilterByBusinessType()) {
			for(BusinessTypeOfEmployee employeeBusiness : listBusinessTypeOfEmployee){
				for(String empBusiness : alarmCheckTargetConRc.getLstBusinessTypeCode()){
					if(employeeBusiness.getBusinessTypeCode().v().equals(empBusiness)) {
						listBusinessTypeOutput.add(employeeBusiness.getSId());
					}
				}
			}
			listFilterOutput.add(listBusinessTypeOutput);
		}
		//employee classification
		if(alarmCheckTargetConRc.isFilterByClassification()) {
			 List<ExClassificationHistoryImport> lstExClassificationHistoryImport = employeeGeneralInfoImport.getExClassificationHistoryImports().stream()
					.collect(Collectors.toList());
			for(ExClassificationHistoryImport employeeclass : lstExClassificationHistoryImport){
				for(String empClass : alarmCheckTargetConRc.getLstClassificationCode()){
					if(empClass.equals(employeeclass.getClassificationItems().get(0).getClassificationCode())) {
						listClassificationCodeOutput.add(employeeclass.getEmployeeId());
					}
				}
			}
			listFilterOutput.add(listClassificationCodeOutput);
		}
		//employee employment
		if(alarmCheckTargetConRc.isFilterByEmployment()) {
			List<ExEmploymentHistoryImport> lstExEmploymentHistoryImport = employeeGeneralInfoImport.getEmploymentHistoryImports().stream()
					.collect(Collectors.toList());
			for(ExEmploymentHistoryImport employeeEmployment : lstExEmploymentHistoryImport){
				for(String empEmployment : alarmCheckTargetConRc.getLstEmploymentCode()){
					if(empEmployment.equals(employeeEmployment.getEmploymentItems().get(0).getEmploymentCode())) {
						listEmploymentOutput.add(employeeEmployment.getEmployeeId());
					}
				}
			}
			listFilterOutput.add(listEmploymentOutput);
		}
		//employee JobTitle
		if(alarmCheckTargetConRc.isFilterByJobTitle()) {
			List<ExJobTitleHistoryImport> lstExJobTitleHistoryImport = employeeGeneralInfoImport.getExJobTitleHistoryImports().stream()
					.collect(Collectors.toList());
			for(ExJobTitleHistoryImport employeeJobTitle : lstExJobTitleHistoryImport){
				for(String empJobTitle : alarmCheckTargetConRc.getLstJobTitleId()){
					if(empJobTitle.equals(employeeJobTitle.getJobTitleItems().get(0).getJobTitleId())) {
						listJobTitleOutput.add(employeeJobTitle.getEmployeeId());
					}
				}
			}
			listFilterOutput.add(listJobTitleOutput);
		}
		
		//filer
		int size = listFilterOutput.size();
		if(listFilterOutput.isEmpty()) {
			return employeeIds;
		}else if(size==1) {
			return listFilterOutput.get(0);
		}
		List<String> listFilterIntersection = listFilterOutput.get(0);
		for (int i = 1; i < size; i++) {
			listFilterIntersection = this.intersection(listFilterIntersection,listFilterOutput.get(i));
		}
		return listFilterIntersection;
	}
	
	private <T> List<T> intersection(List<T> list1, List<T> list2) {
	    List<T> list = new ArrayList<>();

	    for (T t : list1) {
	        if (list2.contains(t)) {
	            list.add(t);
	        }
	    }
	    return list;
	}
	

}
