package nts.uk.ctx.at.record.app.find.log;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.log.dto.EmpCalAndSumExeLogDto;
import nts.uk.ctx.at.record.app.find.stamp.StampDto;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpCalAndSumExeLogFinder {
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo;
	/**
	 * get All EmpCalAndSumExeLog
	 * @param operationCaseID
	 * @param employeeID
	 * @return list EmpCalAndSumExeLog
	 */
	List<EmpCalAndSumExeLogDto> getAllEmpCalAndSumExeLog(String operationCaseID,String employeeID){
		String companyId = AppContexts.user().companyId();
		List<EmpCalAndSumExeLogDto> data = empCalAndSumExeLogRepo
				.getAllEmpCalAndSumExeLog(companyId, operationCaseID, employeeID)
				.stream()
				.map(c -> EmpCalAndSumExeLogDto.fromDomain(c))
				.collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	EmpCalAndSumExeLogDto getEmpCalAndSumExeLogById(String companyID, long empCalAndSumExecLogID,String operationCaseID,String employeeID){
		String companyId = AppContexts.user().companyId();
		Optional<EmpCalAndSumExeLogDto> data = empCalAndSumExeLogRepo
				.getEmpCalAndSumExeLogByID(companyId, empCalAndSumExecLogID, operationCaseID, employeeID)
				.map(c->EmpCalAndSumExeLogDto.fromDomain(c));
			if(data.isPresent())
				return data.get();
		return null;
		
	}
	

}
