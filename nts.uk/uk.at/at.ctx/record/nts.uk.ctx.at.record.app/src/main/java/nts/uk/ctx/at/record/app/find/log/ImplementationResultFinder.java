package nts.uk.ctx.at.record.app.find.log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.log.dto.PersonInfoErrMessageLogDto;
import nts.uk.ctx.at.record.app.find.log.dto.PersonInfoErrMessageLogResultDto;
import nts.uk.ctx.at.record.app.find.log.dto.ScreenImplementationResultDto;
import nts.uk.ctx.at.record.dom.adapter.person.EmpBasicInfoImport;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class ImplementationResultFinder {
	
	@Inject
	private PersonInfoAdapter personInfoAdapter;
	
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;
	
	public PersonInfoErrMessageLogResultDto getScreenImplementationResult(ScreenImplementationResultDto screenImplementationResultDto) {
		
		PersonInfoErrMessageLogResultDto personInfoErrMessageLogResultDto = new PersonInfoErrMessageLogResultDto();
		
		// Get List ErrMessageInfo
		Map<String,List<ErrMessageInfo>> listErrMessageInfo = errMessageInfoRepository.getErrMessageInfoByExecutionContent(screenImplementationResultDto.getEmpCalAndSumExecLogID(), screenImplementationResultDto.getExecutionContent())
				.stream().collect(Collectors.groupingBy(item -> item.getEmployeeID()));
		
		// Get distinct list EmployeeID
		List<String> listEmployeeID = listErrMessageInfo.keySet().stream().collect(Collectors.toList());
		
		List<String> listEmployeeIDToSelect = listEmployeeID.size() > 500 ? listEmployeeID.subList(0, 500) : listEmployeeID;
		List<String> listEmployeeIDToUI = listEmployeeID.size() > 500 ? listEmployeeID.subList(500, listEmployeeID.size()) : new ArrayList<>();
		
		personInfoErrMessageLogResultDto.setListEmployee(listEmployeeIDToUI);
				
		// Get PersonInfo from list EmployeeID
		List<EmpBasicInfoImport> listPersonInfo = personInfoAdapter.getListPersonInfo(listEmployeeIDToSelect);
		
		// Build result
		List<PersonInfoErrMessageLogDto> result = new ArrayList<PersonInfoErrMessageLogDto>();
		for (String errMessageInfo: listEmployeeIDToSelect) {
			List<PersonInfoErrMessageLogDto> results = new ArrayList<PersonInfoErrMessageLogDto>();
			listErrMessageInfo.get(errMessageInfo).stream().forEach(item -> {
				EmpBasicInfoImport employeeLog = listPersonInfo.stream().filter(c -> c.getEmployeeId().equals(item.getEmployeeID())).findAny().get();
				PersonInfoErrMessageLogDto personInfoErrMessageLogDto = new PersonInfoErrMessageLogDto(
						employeeLog.getEmployeeCode(), employeeLog.getPName(), item.getDisposalDay(), item.getMessageError().v());		
				results.add(personInfoErrMessageLogDto);
			});
			result.addAll(results);	
		}
		
		result = result.stream().sorted(Comparator.comparing(PersonInfoErrMessageLogDto::getPersonCode)).limit(100).collect(Collectors.toList());
		
		personInfoErrMessageLogResultDto.setListResult(result);

		return personInfoErrMessageLogResultDto;
		
	}

	
	public PersonInfoErrMessageLogResultDto getScreenImplementationResultWithEmployees(ScreenImplementationResultDto screenImplementationResultDto) {
		
		PersonInfoErrMessageLogResultDto personInfoErrMessageLogResultDto = new PersonInfoErrMessageLogResultDto();
		
		// Get List ErrMessageInfo
		Map<String,List<ErrMessageInfo>> listErrMessageInfo = errMessageInfoRepository.getErrMessageInfoByExecutionContent(screenImplementationResultDto.getEmpCalAndSumExecLogID(), screenImplementationResultDto.getExecutionContent())
				.stream().collect(Collectors.groupingBy(item -> item.getEmployeeID()));
		
		// Get distinct list EmployeeID
		List<String> listEmployeeID = screenImplementationResultDto.getEmployeeID();
		
		List<String> listEmployeeIDToSelect = listEmployeeID.size() > 500 ? listEmployeeID.subList(0, 500) : listEmployeeID;
		List<String> listEmployeeIDToUI = listEmployeeID.size() > 500 ? listEmployeeID.subList(500, listEmployeeID.size()) : new ArrayList<>();
		
		personInfoErrMessageLogResultDto.setListEmployee(listEmployeeIDToUI);
				
		// Get PersonInfo from list EmployeeID
		List<EmpBasicInfoImport> listPersonInfo = personInfoAdapter.getListPersonInfo(listEmployeeIDToSelect);
		
		// Build result
		List<PersonInfoErrMessageLogDto> result = new ArrayList<PersonInfoErrMessageLogDto>();
		for (String errMessageInfo: listEmployeeIDToSelect) {
			List<PersonInfoErrMessageLogDto> results = new ArrayList<PersonInfoErrMessageLogDto>();
			listErrMessageInfo.get(errMessageInfo).stream().forEach(item -> {
				EmpBasicInfoImport employeeLog = listPersonInfo.stream().filter(c -> c.getEmployeeId().equals(item.getEmployeeID())).findAny().get();
				PersonInfoErrMessageLogDto personInfoErrMessageLogDto = new PersonInfoErrMessageLogDto(
						employeeLog.getEmployeeCode(), employeeLog.getPName(), item.getDisposalDay(), item.getMessageError().v());		
				results.add(personInfoErrMessageLogDto);
			});
			result.addAll(results);	
		}
		
		result = result.stream().sorted(Comparator.comparing(PersonInfoErrMessageLogDto::getPersonCode)).limit(100).collect(Collectors.toList());
		
		personInfoErrMessageLogResultDto.setListResult(result);

		return personInfoErrMessageLogResultDto;
		
	}
}
