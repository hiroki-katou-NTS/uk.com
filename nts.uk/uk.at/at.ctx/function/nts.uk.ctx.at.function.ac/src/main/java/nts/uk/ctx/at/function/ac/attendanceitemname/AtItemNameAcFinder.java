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
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemNameImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;

@Stateless
public class AtItemNameAcFinder implements AtItemNameAdapter {

	@Inject
	private AttendanceItemNameService attendanceItemNameService;

	@Override
	public List<AttItemNameImport> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItemImport type) {
		return this.convertAttItem(attendanceItemNameService.getNameOfAttendanceItem(attendanceItemIds,
				EnumAdaptor.valueOf(type.value, TypeOfItem.class)));
	}

	@Override
	public List<AttItemNameImport> getNameOfDailyAttendanceItem(List<DailyAttendanceItem> attendanceItems) {
		List<AttendanceItemName> attItemName = attendanceItems.stream().map(x -> {
			AttendanceItemName dto = new AttendanceItemName();
			dto.setAttendanceItemId(x.getAttendanceItemId());
			dto.setAttendanceItemName(x.getAttendanceName().v());
			dto.setAttendanceItemDisplayNumber(x.getDisplayNumber());
			dto.setNameLineFeedPosition(x.getNameLineFeedPosition());
			dto.setUserCanUpdateAtr(x.getUserCanUpdateAtr().value);
			return dto;
		}).collect(Collectors.toList());

		return this.convertAttItem(attendanceItemNameService.getNameOfAttendanceItem(TypeOfItem.Daily, attItemName));
	}

	@Override
	public List<AttItemNameImport> getNameOfMonthlyAttendanceItem(List<MonthlyAttendanceItem> attendanceItems) {
		List<AttendanceItemName> attItemName = attendanceItems.stream().map(x -> {
			AttendanceItemName dto = new AttendanceItemName();
			dto.setAttendanceItemId(x.getAttendanceItemId());
			dto.setAttendanceItemName(x.getAttendanceName().v());
			dto.setAttendanceItemDisplayNumber(x.getDisplayNumber());
			dto.setNameLineFeedPosition(x.getNameLineFeedPosition());
			dto.setUserCanUpdateAtr(x.getUserCanUpdateAtr().value);
			return dto;
		}).collect(Collectors.toList());

		return this.convertAttItem(attendanceItemNameService.getNameOfAttendanceItem(TypeOfItem.Monthly, attItemName));
	}

	List<AttItemNameImport> convertAttItem(List<AttendanceItemName> attItems) {
		return attItems.stream().map(x -> {
			AttItemNameImport dto = new AttItemNameImport();
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
