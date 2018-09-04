package nts.uk.ctx.at.record.app.find.workrecord.actualsituation.createapproval.monthlyperformance;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyRepository;

@Stateless
public class AppDataInfoMonthlyFinder {
	
	@Inject
	private AppDataInfoMonthlyRepository repo;
	
	@Inject
	private EmployeeRecordAdapter employeeRecordAdapter; 
	
	public List<AppDataInfoMonthlyDto> getListAppDataInfoMonthlyByExecID(String executionId){
		List<AppDataInfoMonthly> data = repo.getAppDataInfoMonthlyByExeID(executionId);
		if(data.isEmpty())
			return Collections.emptyList();
		List<AppDataInfoMonthlyDto> dataFull = data.stream().map(c->AppDataInfoMonthlyDto.fromDomain(c)).collect(Collectors.toList()); 
		List<String> listEmp = dataFull.stream().map(c->c.getEmployeeId()).collect(Collectors.toList());
		List<EmployeeRecordImport> listEmployeeRecord = employeeRecordAdapter.getPersonInfor(listEmp);
		for(AppDataInfoMonthlyDto dto :dataFull) {
			for(EmployeeRecordImport employeeRecordImport : listEmployeeRecord) {
				if(dto.getEmployeeId().equals(employeeRecordImport.getEmployeeId())) {
					dto.setEmployeeCode(employeeRecordImport.getEmployeeCode());
					dto.setPname(employeeRecordImport.getPname());
					break;
				}
			}
		}
		return  dataFull;
	}
	

}
