package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class AttendanceItemsFinder {
	
	@Inject
	private AttendanceItemRepository attendanceItemRepository;
	
	public List<AttendanceItemDto> find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		//勤怠項目
		List<AttendanceItem> attendanceItems = this.attendanceItemRepository.getAttendanceItems(companyId, 1);
		
		List<AttendanceItemDto> attendanceItemDtos = attendanceItems.stream().map(f -> {
			return new AttendanceItemDto(f.getAttendanceId(), f.getAttendanceName().v(), f.getDislayNumber());
		}).collect(Collectors.toList());
		
		return attendanceItemDtos;
	}

}
