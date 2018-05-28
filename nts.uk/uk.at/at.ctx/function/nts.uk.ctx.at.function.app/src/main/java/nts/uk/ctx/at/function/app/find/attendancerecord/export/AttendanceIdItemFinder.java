package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceIdItemFinder {

//	/** The at type. */
//	@Inject
//	private AttendanceTypeDivergenceAdapter atType;
	
	/** The repository. */
	@Inject
	private AttendanceTypeRepository atTypeRepo;

	/** The at name. */
	@Inject
	private DailyAttendanceItemNameDomainService atName;

	public List<AttendanceIdItemDto> getAttendanceItem(List<Integer> screenUse , int attendanceType) {
		String companyId = AppContexts.user().companyId();

		List<AttendanceIdItemDto> attendanceItemList = new ArrayList<AttendanceIdItemDto>();

		//get attendanceId
		screenUse.forEach(item -> {
			attendanceItemList.addAll(atTypeRepo.getItemByAtrandType(companyId, item, attendanceType).stream()
					.map(e -> new AttendanceIdItemDto(e.getAttendanceItemId(), null, item)).collect(Collectors.toList()));
		});

		//get attendanceName
		List<DailyAttendanceItem> dailyAttendanceItems = atName.getNameOfDailyAttendanceItem(
				attendanceItemList.stream().map(e -> e.getAttendanceItemId()).collect(Collectors.toList()));

		//map attendanceId,attendanceName,ScreenUseItem
		attendanceItemList.forEach(attendanceItem -> {
			for (DailyAttendanceItem attendanceName : dailyAttendanceItems) {
				if (attendanceItem.getAttendanceItemId() == attendanceName.getAttendanceItemId())
					attendanceItem.setAttendanceItemName(attendanceName.getAttendanceItemName());
			}
		});

		return attendanceItemList;

	}
}
