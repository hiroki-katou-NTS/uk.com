package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;

@Stateless
public class SingleAttendanceRecordFinder {

	/** The single attendance record repository. */
	@Inject
	private SingleAttendanceRecordRepository singleAttendanceRecordRepository;

	/**
	 * Gets the single attendance record.
	 *
	 * @param code
	 *            the code
	 * @param columnIndex
	 *            the column index
	 * @param position
	 *            the position
	 * @param exportArt
	 *            the export art
	 * @return the single attendance record
	 */
	public SingleAttendanceRecordDto getSingleAttendanceRecord(AttendanceRecordKeyDto attendanceRecordKey) {
		// get domain
		Optional<SingleAttendanceRecord> optionalSingleAttendanceRecord = this.singleAttendanceRecordRepository
				.getSingleAttendanceRecord(attendanceRecordKey.getLayoutId(), attendanceRecordKey.getColumnIndex(),
						attendanceRecordKey.getPosition(), attendanceRecordKey.getExportAtr());
		// convert to Dto
		if (optionalSingleAttendanceRecord.isPresent()) {
			SingleAttendanceRecord singleAttendanceRecord = optionalSingleAttendanceRecord.get();
			int atrribute = singleAttendanceRecord.getAttribute()!=null ? singleAttendanceRecord.getAttribute().value : 0;
			SingleAttendanceRecordDto singleAttendanceRecordDto = new SingleAttendanceRecordDto(
																	singleAttendanceRecord.getName().toString(),
																	singleAttendanceRecord.getTimeItemId(),
																	atrribute);
			return singleAttendanceRecordDto;
		} else {
			return new SingleAttendanceRecordDto();
		}
	}

}
