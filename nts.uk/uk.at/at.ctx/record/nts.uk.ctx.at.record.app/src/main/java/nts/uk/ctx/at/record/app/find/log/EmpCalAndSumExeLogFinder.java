package nts.uk.ctx.at.record.app.find.log;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.log.dto.EmpCalAndSumExeLogDto;
import nts.uk.ctx.at.record.app.find.log.dto.InputEmpCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpCalAndSumExeLogFinder {
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo;
	
	private String companyID = AppContexts.user().companyId();
	private String employeeID = AppContexts.user().employeeId();
	/**
	 * get All EmpCalAndSumExeLog
	 * @param operationCaseID
	 * @param employeeID
	 * @return list EmpCalAndSumExeLog
	 */
	public List<EmpCalAndSumExeLogDto> getAllEmpCalAndSumExeLog(String operationCaseID){
		List<EmpCalAndSumExeLogDto> data = empCalAndSumExeLogRepo
				.getAllEmpCalAndSumExeLog(companyID, operationCaseID, employeeID)
				.stream()
				.map(c -> EmpCalAndSumExeLogDto.fromDomain(c))
				.collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	public EmpCalAndSumExeLogDto getEmpCalAndSumExeLogById(InputEmpCalAndSum inputEmpCalAndSum){
		Optional<EmpCalAndSumExeLogDto> data = empCalAndSumExeLogRepo
				.getEmpCalAndSumExeLogByID(companyID, inputEmpCalAndSum.getEmpCalAndSumExecLogID(), inputEmpCalAndSum.getOperationCaseID(), employeeID)
				.map(c->EmpCalAndSumExeLogDto.fromDomain(c));
			if(data.isPresent())
				return data.get();
		return null;
		
	}
	

}
