package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CalculateAttendanceRecordFinder {

	/** The calculate attendance record repository. */
	@Inject
	private CalculateAttendanceRecordRepositoty calculateAttendanceRecordRepository;

	/** The at name. */
	@Inject
	private AttendanceItemNameDomainService atName;

	/**
	 * Gets the calculate attendance record dto.
	 *
	 * @param attendanceRecordKey
	 *            the attendance record key
	 * @return the calculate attendance record dto
	 */
	public CalculateAttendanceRecordDto getCalculateAttendanceRecordDto(AttendanceRecordKeyDto attendanceRecordKey) {
		// get domain object
		Optional<CalculateAttendanceRecord> optionalCalculateAttendanceRecord = this.calculateAttendanceRecordRepository
				.getCalculateAttendanceRecord(AppContexts.user().companyId(),
						new ExportSettingCode(attendanceRecordKey.getCode()), attendanceRecordKey.getColumnIndex(),
						attendanceRecordKey.getPosition(), attendanceRecordKey.getExportAtr());
		// convert to dto
		CalculateAttendanceRecord calculateAttendanceRecord = optionalCalculateAttendanceRecord.isPresent()
				? optionalCalculateAttendanceRecord.get() : null;

		if (calculateAttendanceRecord != null) {
			List<Integer> listAddedId = calculateAttendanceRecord.getAddedItem();

			List<Integer> listSubtracted = calculateAttendanceRecord.getSubtractedItem();

			List<AttendanceRecordItemDto> listAttendanceAdd = new ArrayList<>();
			List<AttendanceRecordItemDto> listAttendanceSub = new ArrayList<>();

			if (listAddedId != null && !listAddedId.isEmpty()) {
				List<AttendanceRecordItemDto> listAttendanceAdded = findAttendanceItemsById(listAddedId,
						attendanceRecordKey.getExportAtr());
				for (Integer item : listAddedId) {
					for (AttendanceRecordItemDto e : listAttendanceAdded) {
						if (e.getAttendanceItemId() == item)
							listAttendanceAdd.add(e);
					}
				}
			}
			if (listSubtracted != null && !listSubtracted.isEmpty()) {
				List<AttendanceRecordItemDto> listAttendanceSubtracted = findAttendanceItemsById(listSubtracted,
						attendanceRecordKey.getExportAtr());
				for (Integer item : listSubtracted) {
					for (AttendanceRecordItemDto e : listAttendanceSubtracted) {
						if (e.getAttendanceItemId() == item)
							listAttendanceSub.add(e);
					}
				}
			}
			int attribute = calculateAttendanceRecord.getAttribute() != null
					? calculateAttendanceRecord.getAttribute().value : 0;

			CalculateAttendanceRecordDto calculateAttendanceRecordDto = new CalculateAttendanceRecordDto(
					calculateAttendanceRecord.getName().toString(), listAttendanceAdd, listAttendanceSub, attribute);

			return calculateAttendanceRecordDto;
		}
		return null;
	}

	/**
	 * Find attendance items by id.
	 *
	 * @param listAttendanceId
	 *            the list attendance id
	 * @return the list
	 */
	public List<AttendanceRecordItemDto> findAttendanceItemsById(List<Integer> listAttendanceId, Long attendanceType) {
		List<AttendanceRecordItemDto> result = new ArrayList<>();
		result = atName.getNameOfAttendanceItem(listAttendanceId, attendanceType.intValue()).stream()
				.map(e -> new AttendanceRecordItemDto(e.getAttendanceItemId(), e.getAttendanceItemName(), 0,
						e.getTypeOfAttendanceItem()))
				.collect(Collectors.toList());
		return result;
	}

}
