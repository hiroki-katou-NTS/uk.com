package nts.uk.ctx.at.record.app.find.workrecord.erroralarm;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;

@Stateless
public class FixedConditionDataFinder {

	@Inject
	private FixedConditionDataRepository repo;
	
	public List<FixedConditionDataDto> getAllFixedConditionData(){
		List<FixedConditionDataDto> data = repo.getAllFixedConditionData().stream()
				.map(c->FixedConditionDataDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
}
