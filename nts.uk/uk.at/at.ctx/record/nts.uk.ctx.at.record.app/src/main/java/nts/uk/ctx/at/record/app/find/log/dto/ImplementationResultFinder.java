package nts.uk.ctx.at.record.app.find.log.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class ImplementationResultFinder {

	@Inject
	 private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	 private TargetPersonRepository targetPersonRepository;
	
	
	public  ScreenImplementationResultDto getScreenImplementationResult (String empCalAndSumExecLogID ){
		//Get List EmpCalAndSumExeLog
		List<EmpCalAndSumExeLog> listEmpCalAndSumExeLog = empCalAndSumExeLogRepository.getByEmpCalAndSumExecLogID(empCalAndSumExecLogID);
		//Conver to Dto
		List<EmpCalAndSumExeLogDto> listEmpCalAndSumExeLogDto = listEmpCalAndSumExeLog.stream().map(c -> EmpCalAndSumExeLogDto.fromDomain(c)).collect(Collectors.toList());
		//Get List TargetPerson
		List<TargetPerson> listTargetPerSon  = targetPersonRepository.getByempCalAndSumExecLogID(empCalAndSumExecLogID);
		//Convert Dto
		List<TargetPersonDto> listTargetPersonDto = listTargetPerSon.stream().map(c -> TargetPersonDto.fromDomain(c)).collect(Collectors.toList());
		//Get List ExecutionLog
		//List<ExecutionLogDto> listExecutionLog = exe
		
		return new ScreenImplementationResultDto(listEmpCalAndSumExeLogDto, listTargetPersonDto, null);
		
	}
	
	
}
