package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapter;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 * Get Attendance Item List (with Name)
 *
 */
@Stateless
public class AttendanceItemListFinder {
	
	@Inject
	private DailyAttendanceItemNameDomainService dailyAttendanceItemNameDomainService;
	
	@Inject
	private DailyAttendanceItemAdapter dailyAttendanceItemAdapter;
	
	public List<AttendanceItemDto> find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		List<AttendanceItemDto> attendanceItemDtos = new ArrayList<>();
		
		if(this.dailyAttendanceItemAdapter.getDailyAttendanceItemList(companyId).isEmpty()){
			return attendanceItemDtos;
		}
		List<Integer> attendanceItemIds = this.dailyAttendanceItemAdapter.getDailyAttendanceItemList(companyId).stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());
		
		this.dailyAttendanceItemNameDomainService.getNameOfDailyAttendanceItem(attendanceItemIds).forEach(f -> {
			AttendanceItemDto attendanceItemDto = new AttendanceItemDto();
			attendanceItemDto.setAttendanceItemId(f.getAttendanceItemId());
			attendanceItemDto.setAttendanceItemName(f.getAttendanceItemName());
			attendanceItemDto.setAttendanceItemDisplayNumber(f.getAttendanceItemDisplayNumber());
			attendanceItemDtos.add(attendanceItemDto);
		});
		
		return attendanceItemDtos;
		
	}

}
