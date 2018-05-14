package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapterDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SingleAttendanceRecordFinder {

	/** The single attendance record repository. */
	@Inject
	private SingleAttendanceRecordRepository singleAttendanceRecordRepository;

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
	
	/**
	 * Gets the single attendance record.
	 *
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param exportArt the export art
	 * @return the single attendance record
	 */
	public SingleAttendanceRecordDto getSingleAttendanceRecord(String code, int columnIndex, int position,int exportArt) {
		//get domain
		SingleAttendanceRecord singleAttendanceRecord = this.singleAttendanceRecordRepository
										.getSingleAttendanceRecord(AppContexts.user().companyId(), new ExportSettingCode(code), columnIndex, position, exportArt);
		//convert to Dto
		SingleAttendanceRecordDto singleAttendanceRecordDto = new 
				SingleAttendanceRecordDto(singleAttendanceRecord.getName().toString(), singleAttendanceRecord.getTimeItemId(), singleAttendanceRecord.getAttribute().value);
		//return
		return singleAttendanceRecordDto;
	}
	

}
