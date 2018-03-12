package nts.uk.ctx.at.function.app.find.alarm.extraprocessstatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AlarmListExtraProcessStatusFindler {

	@Inject
	private AlarmListExtraProcessStatusRepository repo;
	
	public List<AlarmListExtraProcessStatusDto> getAllElExProcess() {
		String companyID = AppContexts.user().companyId();
		List<AlarmListExtraProcessStatusDto> data = repo.getAllAlListExtaProcess(companyID).stream()
				.map(c->AlarmListExtraProcessStatusDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
		
	}
	
	public AlarmListExtraProcessStatusDto getElExProcessByCode(InputAlExProcess inputAlExProcess) {
		String companyID = AppContexts.user().companyId();
		Optional<AlarmListExtraProcessStatusDto> data = repo.getAlListExtaProcess(companyID, inputAlExProcess.getStartDate(), inputAlExProcess.getStartTime())
				.map(c->AlarmListExtraProcessStatusDto.fromDomain(c));
		if(data.isPresent())
			return data.get();
		return null;
		
	}
	
	
}
