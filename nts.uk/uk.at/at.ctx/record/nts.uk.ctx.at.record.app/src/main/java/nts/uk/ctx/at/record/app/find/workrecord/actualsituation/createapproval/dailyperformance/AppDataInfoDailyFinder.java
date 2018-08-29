package nts.uk.ctx.at.record.app.find.workrecord.actualsituation.createapproval.dailyperformance;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDailyRepository;

@Stateless
public class AppDataInfoDailyFinder {
	
	@Inject
	private AppDataInfoDailyRepository repo;
	
	@Inject
	private EmployeeRecordAdapter employeeRecordAdapter; 
	
	public List<AppDataInfoDailyDto> getListAppDataInfoDailyByExecID(String executionId){
		List<AppDataInfoDaily> data = repo.getAppDataInfoDailyByExeID(executionId);
		if(data.isEmpty())
			return Collections.emptyList();
		List<AppDataInfoDailyDto> dataFull = data.stream().map(c->AppDataInfoDailyDto.fromDomain(c)).collect(Collectors.toList()); 
		List<String> listEmp = dataFull.stream().map(c->c.getEmployeeId()).collect(Collectors.toList());
		List<EmployeeRecordImport> listEmployeeRecord = employeeRecordAdapter.getPersonInfor(listEmp);
		for(AppDataInfoDailyDto dto :dataFull) {
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
