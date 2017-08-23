package nts.uk.ctx.at.shared.app.attendanceitem.find;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.attendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ControlOfAttendanceItemsFinder {
	@Inject
	private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;
	@Inject
	private AttendanceItemRepository attendanceRepo;

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

		Optional<AttendanceItem> attendanceItemDetailOptional = attendanceRepo
				.getAttendanceItemDetail(AppContexts.user().companyId(), attendanceItemId);
		if (attendanceItemDetailOptional.isPresent()) {
			AttendanceItem attendanceItem = attendanceItemDetailOptional.get();
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
