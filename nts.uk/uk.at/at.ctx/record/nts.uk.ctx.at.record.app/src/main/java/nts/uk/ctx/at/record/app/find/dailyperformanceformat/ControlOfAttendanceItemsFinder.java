package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ControlOfAttendanceItemsFinder {
	@Inject
	private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;
	@Inject
	private AttendanceItemRepository attendanceRepo;
	
	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	public ControlOfAttendanceItemsDto getControlOfAttendanceItem(int attendanceItemId) {
		Optional<ControlOfAttendanceItems> controlOfAttendanceItemsOptional = this.controlOfAttendanceItemsRepository
				.getControlOfAttendanceItem(new BigDecimal(attendanceItemId));
		String attendanceName = "";
		int nameLineFeedPosition = 0;
		int inputUnitOfTimeItem = -1;
		String headerBackgroundColorOfDailyPerformance = "";
		if (controlOfAttendanceItemsOptional.isPresent()) {
			ControlOfAttendanceItems controlOfAttendanceItems = controlOfAttendanceItemsOptional.get();
			inputUnitOfTimeItem = controlOfAttendanceItems.getInputUnitOfTimeItem() == null ? -1
					: controlOfAttendanceItems.getInputUnitOfTimeItem().value;
			headerBackgroundColorOfDailyPerformance = controlOfAttendanceItems
					.getHeaderBackgroundColorOfDailyPerformance() == null ? ""
							: controlOfAttendanceItems.getHeaderBackgroundColorOfDailyPerformance().v();
		}

		Optional<DailyAttendanceItem> attendanceItemDetailOptional = dailyAttendanceItemRepository
				.getDailyAttendanceItem(AppContexts.user().companyId(), attendanceItemId);
		if (attendanceItemDetailOptional.isPresent()) {
			DailyAttendanceItem attendanceItem = attendanceItemDetailOptional.get();
			attendanceName = attendanceItem.getAttendanceName().v();
			nameLineFeedPosition = attendanceItem.getNameLineFeedPosition();
		}

		ControlOfAttendanceItemsDto controlOfAttendanceItemsDto = new ControlOfAttendanceItemsDto(attendanceItemId,
				attendanceName, inputUnitOfTimeItem, headerBackgroundColorOfDailyPerformance, nameLineFeedPosition);

		return controlOfAttendanceItemsDto;
	}

	/*
	 * private ControlOfAttendanceItemsDto toControlOfAttendanceItemsDto(
	 * ControlOfAttendanceItems controlOfAttendanceItems) { return new
	 * ControlOfAttendanceItemsDto(controlOfAttendanceItems.getAttandanceTimeId(
	 * ).intValue(), controlOfAttendanceItems.getInputUnitOfTimeItem().value,
	 * controlOfAttendanceItems.getHeaderBackgroundColorOfDailyPerformance().v()
	 * , controlOfAttendanceItems.getNameLineFeedPosition()); }
	 */
}
