package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceIdItemFinder {

	// /** The at type. */
	// @Inject
	// private AttendanceTypeDivergenceAdapter atType;

	/** The repository. */
	@Inject
	private AttendanceTypeRepository atTypeRepo;

	/** The at name. */
	@Inject
	private AttendanceItemNameDomainService atName;

	/**
	 * Gets the attendance item.
	 *
	 * @param screenUse
	 *            the screen use
	 * @param attendanceType
	 *            the attendance type
	 * @return the attendance item
	 */
	public List<AttendanceIdItemDto> getAttendanceItem(List<Integer> screenUse, int attendanceType) {
		String companyId = AppContexts.user().companyId();

		List<AttendanceIdItemDto> attendanceItemList = new ArrayList<AttendanceIdItemDto>();

		// get attendanceId
		screenUse.forEach(item -> {
			attendanceItemList.addAll(atTypeRepo.getItemByAtrandType(companyId, item, attendanceType).stream()
					.map(e -> new AttendanceIdItemDto(e.getAttendanceItemId(), null, item))
					.collect(Collectors.toList()));
		});

		// get attendanceName
		List<AttendanceItemName> dailyAttendanceItems = atName.getNameOfAttendanceItem(
				attendanceItemList.stream().map(e -> e.getAttendanceItemId()).collect(Collectors.toList()),
				attendanceType);

		// map attendanceId,attendanceName,ScreenUseItem
		attendanceItemList.forEach(attendanceItem -> {
			for (AttendanceItemName attendanceName : dailyAttendanceItems) {
				if (attendanceItem.getAttendanceItemId() == attendanceName.getAttendanceItemId())
					attendanceItem.setAttendanceItemName(attendanceName.getAttendanceItemName());
			}
		});

		return attendanceItemList.stream().filter(e -> e.getAttendanceItemName() != null)
				.collect(Collectors.toList());

	}
}
