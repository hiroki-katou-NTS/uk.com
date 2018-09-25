package nts.uk.ctx.at.function.ac.attendanceitemname;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;

@Stateless
public class AtItemNameAcFinder implements AtItemNameAdapter {

	@Inject
	private AttendanceItemNameService attendanceItemNameService;

	@Override
	public List<AttItemName> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItemImport type) {
		return attendanceItemNameService.getNameOfAttendanceItem(attendanceItemIds,
				EnumAdaptor.valueOf(type.value, TypeOfItem.class));
	}

	@Override
	public List<AttItemName> getNameOfDailyAttendanceItem(List<DailyAttendanceItem> attendanceItems) {
		List<AttItemName> attItemName = attendanceItems.stream().map(x -> {
			AttItemName dto = new AttItemName();
			dto.setAttendanceItemId(x.getAttendanceItemId());
			dto.setAttendanceItemName(x.getAttendanceName().v());
			dto.setAttendanceItemDisplayNumber(x.getDisplayNumber());
			dto.setNameLineFeedPosition(x.getNameLineFeedPosition());
			dto.setUserCanUpdateAtr(x.getUserCanUpdateAtr().value);
			return dto;
		}).collect(Collectors.toList());

		return attendanceItemNameService.getNameOfAttendanceItem(TypeOfItem.Daily, attItemName);
	}

	@Override
	public List<AttItemName> getNameOfMonthlyAttendanceItem(List<MonthlyAttendanceItem> attendanceItems) {
		List<AttItemName> attItemName = attendanceItems.stream().map(x -> {
			AttItemName dto = new AttItemName();
			dto.setAttendanceItemId(x.getAttendanceItemId());
			dto.setAttendanceItemName(x.getAttendanceName().v());
			dto.setAttendanceItemDisplayNumber(x.getDisplayNumber());
			dto.setNameLineFeedPosition(x.getNameLineFeedPosition());
			dto.setUserCanUpdateAtr(x.getUserCanUpdateAtr().value);
			return dto;
		}).collect(Collectors.toList());

		return attendanceItemNameService.getNameOfAttendanceItem(TypeOfItem.Monthly, attItemName);
	}

	List<AttItemName> convertAttItem(List<AttendanceItemName> attItems) {
		return attItems.stream().map(x -> {
			AttItemName dto = new AttItemName();
			dto.setAttendanceItemId(x.getAttendanceItemId());
			dto.setAttendanceItemName(x.getAttendanceItemName());
			dto.setAttendanceItemDisplayNumber(x.getAttendanceItemDisplayNumber());
			dto.setTypeOfAttendanceItem(x.getTypeOfAttendanceItem());
			dto.setFrameCategory(x.getFrameCategory());
			dto.setNameLineFeedPosition(x.getNameLineFeedPosition());
			dto.setUserCanUpdateAtr(x.getUserCanUpdateAtr());
			return dto;
		}).collect(Collectors.toList());
	}
}
