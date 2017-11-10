package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;

@Stateless
public class ControlOfAttendanceItemsFinder {
	@Inject
	private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;


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
			nameLineFeedPosition = controlOfAttendanceItems.getNameLineFeedPosition();
		}

	
		ControlOfAttendanceItemsDto controlOfAttendanceItemsDto = new ControlOfAttendanceItemsDto(attendanceItemId,
				attendanceName, inputUnitOfTimeItem, headerBackgroundColorOfDailyPerformance, nameLineFeedPosition);

		return controlOfAttendanceItemsDto;
	}

	
}
