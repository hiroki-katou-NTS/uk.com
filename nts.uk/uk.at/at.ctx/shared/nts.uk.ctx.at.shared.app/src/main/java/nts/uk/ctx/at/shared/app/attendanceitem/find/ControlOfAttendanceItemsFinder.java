package nts.uk.ctx.at.shared.app.attendanceitem.find;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.attendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.ControlOfAttendanceItemsRepository;

@Stateless
public class ControlOfAttendanceItemsFinder {
	@Inject
	private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;

	public ControlOfAttendanceItemsDto getControlOfAttendanceItem(int attendanceItemId) {
		Optional<ControlOfAttendanceItems> controlOfAttendanceItemsOptional = this.controlOfAttendanceItemsRepository.getControlOfAttendanceItem(new BigDecimal(attendanceItemId));
		if (controlOfAttendanceItemsOptional.isPresent()) {
			return this.toControlOfAttendanceItemsDto(controlOfAttendanceItemsOptional.get());
		}
		return null;
	}

	private ControlOfAttendanceItemsDto toControlOfAttendanceItemsDto(
			ControlOfAttendanceItems controlOfAttendanceItems) {
		return new ControlOfAttendanceItemsDto(controlOfAttendanceItems.getAttandanceTimeId().intValue(),
				controlOfAttendanceItems.getInputUnitOfTimeItem().value,
				controlOfAttendanceItems.getHeaderBackgroundColorOfDailyPerformance().v(),controlOfAttendanceItems.getNameLineFeedPosition());
	}
}
