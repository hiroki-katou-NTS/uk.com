package nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyAttendanceItemSharedFinder {
	
	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItem;
	
	public List<DailyAttendanceItemDto> getAllDailyAttdItem(){
		String companyID = AppContexts.user().companyId();
		List<DailyAttendanceItemDto> data = dailyAttendanceItem.getList(companyID).stream()
				.map(c->DailyAttendanceItemDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
		
	}

}
