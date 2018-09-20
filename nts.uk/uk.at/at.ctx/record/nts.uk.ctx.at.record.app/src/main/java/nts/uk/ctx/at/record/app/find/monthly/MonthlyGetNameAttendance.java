package nts.uk.ctx.at.record.app.find.monthly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class MonthlyGetNameAttendance {

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;
	
	public List<AttdItemDto> getListAttendanceItemName(){
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		List<AttdItemDto> listAttendanceItemName = companyMonthlyItemService.getMonthlyItems(companyId,  Optional.empty(), null, null).stream()
				.map(x -> new AttdItemDto(x.getAttendanceItemId(), x.getAttendanceItemName(), x.getAttendanceItemDisplayNumber(),
						x.getNameLineFeedPosition(), x.getAttendanceItemDisplayNumber(), x.getAttendanceItemDisplayNumber(), x.getUserCanUpdateAtr())).collect(Collectors.toList());
		return listAttendanceItemName;
	}
}
