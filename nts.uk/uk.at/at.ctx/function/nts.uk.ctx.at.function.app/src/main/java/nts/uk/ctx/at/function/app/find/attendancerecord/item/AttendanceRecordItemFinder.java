package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceRecordItemFinder {

	// /** The at type. */
	// @Inject
	// private AttendanceTypeDivergenceAdapter atType;

	@Inject
	private AttendanceTypeRepository attendanceTypeRepository;

	/** The at name. */
	@Inject
	private DailyAttendanceItemNameDomainService dailyAtName;

	/** The at name. */
	@Inject
	private AttendanceItemNameDomainService atName;

	/**
	 * Gets the attnd items by atr and type.
	 *
	 * @param screenUseAtr
	 *            the screen use atr
	 * @param attendanceType
	 *            the attendance type
	 * @return the attnd items by atr and type
	 */
	public List<AttendanceRecordItemDto> getAttndItemsByAtrAndType(AttendanceTypeKeyDto attendanceTypeKey) {
		List<AttendanceRecordItemDto> listAttendanceRecordItem = new ArrayList<AttendanceRecordItemDto>();
		String companyId = AppContexts.user().companyId();
		// get list AttendanceItemId by screenUseAtr,attendanceType
		List<Integer> attendanceItemIds = attendanceTypeRepository
				.getItemByAtrandType(companyId, attendanceTypeKey.getScreenUseAtr(),
						attendanceTypeKey.getAttendanceType())
				.stream().map(e -> e.getAttendanceItemId()).collect(Collectors.toList());
		// get list AttendanceItem and convert to list attendancerecordItemDto
		listAttendanceRecordItem = atName
				.getNameOfAttendanceItem(attendanceItemIds, attendanceTypeKey.getAttendanceType()).stream()
				.map(e -> new AttendanceRecordItemDto(e.getAttendanceItemId(), e.getAttendanceItemName(),
						attendanceTypeKey.getScreenUseAtr(), e.getTypeOfAttendanceItem()))
				.collect(Collectors.toList());
		return listAttendanceRecordItem;
	}

	/**
	 * Gets the attendance items by screen use atr.
	 *
	 * @param screenUseAtr
	 *            the screen use atr
	 * @return the attendance items by screen use atr
	 */
	public List<AttendanceRecordItemDto> getAttendanceItemsByScreenUseAtr(int screenUseAtr) {
		List<AttendanceRecordItemDto> listAttendanceRecordItem = new ArrayList<AttendanceRecordItemDto>();
		String companyId = AppContexts.user().companyId();
		// get list AttendanceItem by screenUseAtr
		List<Integer> attendanceItemIds = attendanceTypeRepository.getItemByScreenUseAtr(companyId, screenUseAtr)
				.stream().map(e -> e.getAttendanceItemId()).collect(Collectors.toList());
		// get list AttendanceItem and convert to list attendancerecordItemDto
		listAttendanceRecordItem = dailyAtName.getNameOfDailyAttendanceItem(attendanceItemIds).stream()
				.map(e -> new AttendanceRecordItemDto(e.getAttendanceItemId(), e.getAttendanceItemName(), screenUseAtr,
						e.getTypeOfAttendanceItem()))
				.collect(Collectors.toList());
		return listAttendanceRecordItem;
	}

	/**
	 * Find attendance items by id.
	 *
	 * @param listAttendanceId
	 *            the list attendance id
	 * @return the list
	 */
	public List<AttendanceRecordItemDto> findAttendanceItemsById(List<Integer> listAttendanceId) {
		List<AttendanceRecordItemDto> result = new ArrayList<>();
		result = dailyAtName.getNameOfDailyAttendanceItem(listAttendanceId).stream()
				.map(e -> new AttendanceRecordItemDto(e.getAttendanceItemId(), e.getAttendanceItemName(), 0,
						e.getTypeOfAttendanceItem()))
				.collect(Collectors.toList());
		return result;
	}

	public List<AttendanceRecordItemDto> getAllAttendanceDaily(List<Integer> listScreenUseAtr) {
		String companyId = AppContexts.user().companyId();
		List<AttendanceRecordItemDto> attendanceItemList = new ArrayList<AttendanceRecordItemDto>();
		// get attendanceId
		listScreenUseAtr.forEach(item -> {
			attendanceItemList.addAll(attendanceTypeRepository.getItemByScreenUseAtr(companyId, item).stream()
					.map(e -> new AttendanceRecordItemDto(e.getAttendanceItemId(), null, item,
							e.getAttendanceItemType().value))
					.collect(Collectors.toList()));
		});
		// get attendanceName
		List<DailyAttendanceItem> dailyAttendanceItems = dailyAtName.getNameOfDailyAttendanceItem(
				attendanceItemList.stream().map(e -> e.getAttendanceItemId()).collect(Collectors.toList()));
		// map attendanceId,attendanceName,ScreenUseItem
		attendanceItemList.forEach(attendanceItem -> {
			for (DailyAttendanceItem attendanceName : dailyAttendanceItems) {
				if (attendanceItem.getAttendanceItemId() == attendanceName.getAttendanceItemId())
					attendanceItem.setAttendanceItemName(attendanceName.getAttendanceItemName());
			}
		});
		return attendanceItemList;
	}

}
