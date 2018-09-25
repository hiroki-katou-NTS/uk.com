package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DailyPerformanceAuthoritySetting {
	
	@Inject
	private DailyAttdItemAuthRepository dailyAttdItemAuthRepository;
	
	@Inject
	private CompanyDailyItemService companyDailyItemService;
	
	public List<AttendanceItemName> getListAttendanceItemName(List<Integer> dailyAttendanceItemIds){
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		Optional<DailyAttendanceItemAuthority> itemAuthority = dailyAttdItemAuthRepository
				.getDailyAttdItemByAttItemId(companyId, "1", dailyAttendanceItemIds);
		
		List<AttendanceItemName> listAttendanceItemName = companyDailyItemService.getDailyItems(companyId, itemAuthority.isPresent() ? Optional.of(itemAuthority.get().getAuthorityDailyId()) : Optional.empty(), null, null).stream()
				.map(x -> new AttendanceItemName(x.getAttendanceItemId(), x.getAttendanceItemName(), x.getAttendanceItemDisplayNumber(),
						x.getUserCanUpdateAtr(), x.getNameLineFeedPosition(), x.getTypeOfAttendanceItem(), x.getFrameCategory())).collect(Collectors.toList());
		return listAttendanceItemName;
	}

}
