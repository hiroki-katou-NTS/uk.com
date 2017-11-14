package nts.uk.ctx.at.record.app.find.log;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.record.app.find.log.dto.EmpCalAndSumExeLogDto;
import nts.uk.ctx.at.record.app.find.log.dto.ErrMessageInfoDto;
import nts.uk.ctx.at.record.app.find.log.dto.ScreenImplementationResultDto;
import nts.uk.ctx.at.record.app.find.log.dto.TargetPersonDto;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;

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
	
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;
	
	
	public ScreenImplementationResultDto getScreenImplementationResult (String empCalAndSumExecLogID ){
		//Get List EmpCalAndSumExeLog
		Optional<EmpCalAndSumExeLog> listEmpCalAndSumExeLog = empCalAndSumExeLogRepository.getByEmpCalAndSumExecLogID(empCalAndSumExecLogID);
		//Conver to Dto
		Optional<EmpCalAndSumExeLogDto> empCalAndSumExeLogDto = listEmpCalAndSumExeLog.map(c -> EmpCalAndSumExeLogDto.fromDomain(c));
		//Get List TargetPerson
		List<TargetPerson> listTargetPerSon  = targetPersonRepository.getByempCalAndSumExecLogID(empCalAndSumExecLogID);
		//Convert Dto
		List<TargetPersonDto> listTargetPersonDto = listTargetPerSon.stream().map(c -> TargetPersonDto.fromDomain(c)).collect(Collectors.toList());
		//Get List Enum ComboBox
		List<EnumConstant> enumComboBox =EnumAdaptor.convertToValueNameList(ExecutionContent.class);
		//Get List ErrMessageInfo
		List<ErrMessageInfo> listErrMessageInfo = errMessageInfoRepository.getAllErrMessageInfoByEmpID(empCalAndSumExecLogID);
		//Conver to Dto
		List<ErrMessageInfoDto> listErrMessageInfoDto = listErrMessageInfo.stream().map(c -> ErrMessageInfoDto.fromDomain(c)).collect(Collectors.toList());
		 
		return new ScreenImplementationResultDto(empCalAndSumExeLogDto.get(), listTargetPersonDto ,enumComboBox,listErrMessageInfoDto);		
		
	}
}
