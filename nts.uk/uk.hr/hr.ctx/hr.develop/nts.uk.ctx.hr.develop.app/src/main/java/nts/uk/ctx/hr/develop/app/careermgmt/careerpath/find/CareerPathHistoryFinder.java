package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto.DateHistoryItemDto;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.DateHistoryItem;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpathhistory.CareerPathHistService;

@Stateless
public class CareerPathHistoryFinder {

	@Inject
	private CareerPathHistService careerPathHistService;
	
	public List<DateHistoryItemDto> getCareerPathHistory(String companyId){
		List<DateHistoryItem> a = careerPathHistService.getCareerPathHistList(companyId);
		return careerPathHistService.getCareerPathHistList(companyId).stream().map(c->new DateHistoryItemDto(c.getPeriod().start(), c.getPeriod().end(), c.getHistoryId()))
				.collect(Collectors.toList());
	}
}
