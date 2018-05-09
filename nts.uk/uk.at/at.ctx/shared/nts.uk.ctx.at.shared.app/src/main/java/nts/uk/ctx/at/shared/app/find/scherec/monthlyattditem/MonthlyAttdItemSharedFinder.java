package nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
@Stateless
public class MonthlyAttdItemSharedFinder {

	@Inject
	private MonthlyAttendanceItemRepository monthlyRepo;
	
	public List<MonthlyAttdItemSharedDto> getAllMonthlyAttdItemShared(){
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<MonthlyAttdItemSharedDto> attendanceItemDtos = this.monthlyRepo.findAll(companyId).stream().map(dom -> this.toDto(dom))
				.collect(Collectors.toList());

		return attendanceItemDtos;
	}
	
	
	private MonthlyAttdItemSharedDto toDto(MonthlyAttendanceItem dom) {
		MonthlyAttdItemSharedDto attdItemDto = new MonthlyAttdItemSharedDto();
		attdItemDto.setAttendanceItemDisplayNumber(dom.getDisplayNumber());
		attdItemDto.setAttendanceItemId(dom.getAttendanceItemId());
		attdItemDto.setAttendanceItemName(dom.getAttendanceName().v());
		attdItemDto.setDailyAttendanceAtr(dom.getMonthlyAttendanceAtr().value);
		attdItemDto.setNameLineFeedPosition(dom.getNameLineFeedPosition());
		attdItemDto.setDisplayNumber(dom.getDisplayNumber());
		return attdItemDto;

	}
}
