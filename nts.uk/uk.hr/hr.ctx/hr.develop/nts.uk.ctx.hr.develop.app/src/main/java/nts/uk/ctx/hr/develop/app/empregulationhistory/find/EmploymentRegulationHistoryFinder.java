package nts.uk.ctx.hr.develop.app.empregulationhistory.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.empregulationhistory.dto.DateHistoryItemDto;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryService;

@Stateless
public class EmploymentRegulationHistoryFinder {

	@Inject
	private EmploymentRegulationHistoryService service;
	
	public List<DateHistoryItemDto> getList(String companyId){
		return service.getEmpRegulationHistList(companyId).stream().map(c -> new DateHistoryItemDto(c.identifier(), c.start(), c.end())).collect(Collectors.toList());
	}
}

