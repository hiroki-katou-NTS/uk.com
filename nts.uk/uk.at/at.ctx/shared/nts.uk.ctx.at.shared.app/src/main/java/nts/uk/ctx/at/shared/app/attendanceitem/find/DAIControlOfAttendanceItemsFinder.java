package nts.uk.ctx.at.shared.app.attendanceitem.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.attendanceitem.DisplayAndInputControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.DAIControlOfAttendanceItemsRepository;

@Stateless
public class DAIControlOfAttendanceItemsFinder {
	@Inject
	private DAIControlOfAttendanceItemsRepository dAIControlOfAttendanceItemsRepository;

	public List<DAIControlOfAttendanceItemsDto> getListControlOfAttendanceItem(String workTypeCode) {
		List<DisplayAndInputControlOfAttendanceItems> lstControlOfAttendanceItem = this.dAIControlOfAttendanceItemsRepository
				.getListControlOfAttendanceItem(new BusinessTypeCode(workTypeCode));
		return lstControlOfAttendanceItem.stream().map(c -> toDAIControlOfAttendanceItemsDto(c))
				.collect(Collectors.toList());
	}
	private DAIControlOfAttendanceItemsDto toDAIControlOfAttendanceItemsDto(
			DisplayAndInputControlOfAttendanceItems displayAndInputControlOfAttendanceItems) {
		return new DAIControlOfAttendanceItemsDto(displayAndInputControlOfAttendanceItems.getAttendanceItemId().intValue(),
				displayAndInputControlOfAttendanceItems.getBusinessTypeCode().v(),
				displayAndInputControlOfAttendanceItems.isYouCanChangeIt(),
				displayAndInputControlOfAttendanceItems.isCanBeChangedByOthers(),displayAndInputControlOfAttendanceItems.isUse());
	}
}
