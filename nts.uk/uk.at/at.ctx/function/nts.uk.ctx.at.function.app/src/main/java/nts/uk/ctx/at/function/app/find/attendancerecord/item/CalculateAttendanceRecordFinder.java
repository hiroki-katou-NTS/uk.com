package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapterDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CalculateAttendanceRecordFinder {
	
	/** The calculate attendance record repository. */
	@Inject
	private CalculateAttendanceRecordRepositoty calculateAttendanceRecordRepository;
	
	/** The at type. */
	@Inject
	private AttendanceTypeDivergenceAdapter atType;
	
	/** The at name. */
	@Inject
	private AttendanceNameDivergenceAdapter atName;

	/**
	 * Gets the attendance by screen use items.
	 *
	 * @param screenUseAtr the screen use atr
	 * @return the attendance by screen use items
	 */
	public List<AttendanceTypeDivergenceAdapterDto> getAttendanceByScreenUseItems(int screenUseAtr) {

		return atType.getItemByScreenUseAtr(AppContexts.user().companyId(), screenUseAtr);
	}
	
	/**
	 * Gets the name attendance.
	 *
	 * @param dailyAttendanceItemIds the daily attendance item ids
	 * @return the name attendance
	 */
	public List<AttendanceNameDivergenceDto> getNameAttendance(List<Integer> dailyAttendanceItemIds){
		return atName.getDailyAttendanceItemName(dailyAttendanceItemIds);
	}
	
	public CalculateAttendanceRecordDto getCalculateAttendanceRecordDto(AttendanceRecordKeyDto attendanceRecordKey) {
		//get domain object
		CalculateAttendanceRecord calculateAttendanceRecord = this.calculateAttendanceRecordRepository.getCalculateAttendanceRecord(
																AppContexts.user().companyId(),
																new ExportSettingCode(attendanceRecordKey.getCode()), 
																attendanceRecordKey.getColumnIndex(),
																attendanceRecordKey.getPosition(),
																attendanceRecordKey.getExportArt());
		//convert to dto
		CalculateAttendanceRecordDto calculateAttendanceRecordDto = new CalculateAttendanceRecordDto(
																	calculateAttendanceRecord.getName().toString(),
																	calculateAttendanceRecord.getAddedItem(),
																	calculateAttendanceRecord.getSubtractedItem(),
																	calculateAttendanceRecord.getAttribute().value);
				
		
		return calculateAttendanceRecordDto;
	}

}
