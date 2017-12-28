package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;

@Stateless
public class WorkRecordExtraConFinder {
	@Inject
	private WorkRecordExtraConRepository repo;
	
	/**
	 * get All Work record extracing condition 
	 */
	public List<WorkRecordExtraConDto> getAllWorkRecordExtraCon(){
		List<WorkRecordExtraConDto> data = repo.getAllWorkRecordExtraCon().stream()
				.map(c->WorkRecordExtraConDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	/**
	 * get All Work record extracing condition by errorAlarmCheckID
	 */
	public WorkRecordExtraConDto getWorkRecordExtraConById(String errorAlarmCheckID) {
		Optional<WorkRecordExtraConDto> data = repo.getWorkRecordExtraConById(errorAlarmCheckID)
				.map(c->WorkRecordExtraConDto.fromDomain(c));
		if(data.isPresent())
			return data.get();
		return null;
	}

}
