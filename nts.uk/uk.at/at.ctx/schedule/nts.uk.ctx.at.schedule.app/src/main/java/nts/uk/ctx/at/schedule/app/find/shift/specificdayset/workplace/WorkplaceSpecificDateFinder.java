package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;

@Stateless
public class WorkplaceSpecificDateFinder {
	@Inject
	WorkplaceSpecificDateRepository workplaceSpecDateRepo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	// WITH name
	public List<WokplaceSpecificDateDto> getWpSpecByDateWithName(String workplaceId, String wpSpecDate) {
		List<WokplaceSpecificDateDto> wokplaceSpecificDateDtos = new ArrayList<WokplaceSpecificDateDto>();
		GeneralDate startDate = GeneralDate.fromString(wpSpecDate, DATE_FORMAT);
		GeneralDate endDate = startDate.addMonths(1).addDays(-1);
		List<WorkplaceSpecificDateItem> resultList = workplaceSpecDateRepo.getWpSpecByDateWithName(workplaceId, startDate, endDate);
		
		while(startDate.beforeOrEquals(endDate)) {	
			GeneralDate tmpDate = startDate;
			List<WorkplaceSpecificDateItem> listByDate = resultList.stream().filter(x -> x.getSpecificDate().equals(tmpDate)).collect(Collectors.toList());
			if(!listByDate.isEmpty()){
				List<Integer> specificDateItemNo = new ArrayList<Integer>();
				for(WorkplaceSpecificDateItem dateRecord: listByDate){
					specificDateItemNo.add(dateRecord.getSpecificDateItemNo().v());
				}
				wokplaceSpecificDateDtos.add(new WokplaceSpecificDateDto(workplaceId, listByDate.get(0).getSpecificDate(), specificDateItemNo));
			}
			startDate = startDate.addDays(1);
		}
		return wokplaceSpecificDateDtos;
	}

}
