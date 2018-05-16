package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CalculateAttendanceRecordFinder {

	/** The calculate attendance record repository. */
	@Inject
	private CalculateAttendanceRecordRepositoty calculateAttendanceRecordRepository;


	public CalculateAttendanceRecordDto getCalculateAttendanceRecordDto(AttendanceRecordKeyDto attendanceRecordKey) {
		// get domain object
		Optional<CalculateAttendanceRecord> optionalCalculateAttendanceRecord = this.calculateAttendanceRecordRepository
				.getCalculateAttendanceRecord(AppContexts.user().companyId(),
						new ExportSettingCode(attendanceRecordKey.getCode()), attendanceRecordKey.getColumnIndex(),
						attendanceRecordKey.getPosition(), attendanceRecordKey.getExportArt());
		// convert to dto
		CalculateAttendanceRecord calculateAttendanceRecord = optionalCalculateAttendanceRecord.isPresent()
				? optionalCalculateAttendanceRecord.get() : new CalculateAttendanceRecord();

		CalculateAttendanceRecordDto calculateAttendanceRecordDto = new CalculateAttendanceRecordDto(
				calculateAttendanceRecord.getName().toString(), calculateAttendanceRecord.getAddedItem(),
				calculateAttendanceRecord.getSubtractedItem(), calculateAttendanceRecord.getAttribute().value);

		return calculateAttendanceRecordDto;
	}

}
