package nts.uk.ctx.at.record.app.find.workrecord.erroralarm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordRepository;

@Stateless
public class FixedConditionWorkRecordFinder {
	
	@Inject
	private FixedConditionWorkRecordRepository repo;
	
	public List<FixedConditionWorkRecordDto> getAllFixedConditionWorkRecord(){
		List<FixedConditionWorkRecordDto> data = repo.getAllFixedConditionWorkRecord().stream()
				.map(c->FixedConditionWorkRecordDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	public List<FixedConditionWorkRecordDto> getAllFixedConWRByAlarmID(String dailyAlarmConID){
		List<FixedConditionWorkRecordDto> data = repo.getAllFixedConWRByAlarmID(dailyAlarmConID).stream()
				.map(c->FixedConditionWorkRecordDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	public FixedConditionWorkRecordDto getFixedConWRByCode(String dailyAlarmConID,int fixConWorkRecordNo){
		Optional<FixedConditionWorkRecordDto> data = repo.getFixedConWRByCode(dailyAlarmConID, fixConWorkRecordNo)
				.map(c->FixedConditionWorkRecordDto.fromDomain(c));
		if(data.isPresent())
			return data.get();
		return null;
	}
	
}
