package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycheckcondition;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMonRepository;

@Stateless
public class FixedExtraItemMonFinder {

	@Inject 
	private FixedExtraItemMonRepository repo;
	
	public List<FixedExtraItemMonDto> getAllFixedExtraItemMon(){
		List<FixedExtraItemMonDto> data = repo.getAllFixedExtraItemMon().stream()
				.map(c->FixedExtraItemMonDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
}
