package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;

@Stateless
public class WorkplaceSpecificDateFinder {
	@Inject
	WorkplaceSpecificDateRepository workplaceSpecDateRepo;
	// WITH name
	public List<WokplaceSpecificDateDto> getWpSpecByDateWithName(String workplaceId, String wpSpecDate) {
		List<WokplaceSpecificDateDto> wokplaceSpecificDateDtos = new ArrayList<WokplaceSpecificDateDto>();
		List<WorkplaceSpecificDateItem> resultList = workplaceSpecDateRepo.getWpSpecByDateWithName(workplaceId, wpSpecDate);
		for(int i=1;i<=31;i++){
			int startMonth = Integer.valueOf(wpSpecDate+String.format("%02d", i));
			List<WorkplaceSpecificDateItem> listByDate = resultList.stream().filter(x -> x.getSpecificDate().v().intValue()==startMonth).collect(Collectors.toList());
			if(!listByDate.isEmpty()){
				List<BigDecimal> specificDateItemNo = new ArrayList<BigDecimal>();
				for(WorkplaceSpecificDateItem dateRecord: listByDate){
					specificDateItemNo.add(dateRecord.getSpecificDateItemNo().v());
				}
				wokplaceSpecificDateDtos.add(new WokplaceSpecificDateDto(workplaceId, listByDate.get(0).getSpecificDate().v(), specificDateItemNo));
			}
		}
		return wokplaceSpecificDateDtos;
	}

}
