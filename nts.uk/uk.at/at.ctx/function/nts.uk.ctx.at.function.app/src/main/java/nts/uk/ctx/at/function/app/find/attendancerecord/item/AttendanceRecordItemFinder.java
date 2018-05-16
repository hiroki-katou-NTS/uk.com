package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceRecordItemFinder {

	/** The at type. */
	@Inject
	private AttendanceTypeDivergenceAdapter atType;

	/** The at name. */
	@Inject
	private DailyAttendanceItemNameDomainService atName;


	/**
	 * Gets the attendance items by screen use atr.
	 *
	 * @param screenUseAtr the screen use atr
	 * @return the attendance items by screen use atr
	 */
	public List<AttendanceRecordItemDto> getAttendanceItemsByScreenUseAtr(int screenUseAtr){
		String companyId = AppContexts.user().companyId();
		//get list AttendanceItem by screenUseAtr
		List<AttendanceRecordItemDto> listAttendanceRecordItem = new ArrayList<AttendanceRecordItemDto>();
		List<Integer> attendanceItemIds = atType.getItemByScreenUseAtr(companyId, screenUseAtr).stream().map(e -> e.getAttendanceItemId()).collect(Collectors.toList());
		//get list AttendanceItem and convert to list attendancerecordItemDto
		listAttendanceRecordItem = atName.getNameOfDailyAttendanceItem(attendanceItemIds).stream().map(
				e -> new AttendanceRecordItemDto(e.getAttendanceItemId(),e.getAttendanceItemName(),screenUseAtr)).collect(Collectors.toList());
		
		return listAttendanceRecordItem;
	}
}
