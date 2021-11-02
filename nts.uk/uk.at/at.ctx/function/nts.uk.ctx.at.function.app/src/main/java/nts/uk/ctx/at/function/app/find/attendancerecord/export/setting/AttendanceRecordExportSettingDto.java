package nts.uk.ctx.at.function.app.find.attendancerecord.export.setting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceRecordExportDto;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordExportSettingDto {

	/** The code. */
	String code;

	/** The name. */
	String name;

	/** The seal stamp. */
	List<String> sealStamp;

	/** The daily export item. */
	List<AttendanceRecordExportDto> dailyExportItem;

	/** The monthly export item. */
	List<AttendanceRecordExportDto> monthlyExportItem;

	Integer nameUseAtr;
	/** The seal use atr. */
	
	Boolean sealUseAtr;
	
	/** The export font size. */
	Integer exportFontSize;
	
	/** The monthly confirmed display. */
	Integer monthlyConfirmedDisplay;
	
	/** The layout id. */
	String layoutId;
	
	/** The item sel type. */
	Integer itemSelType;
	
	Integer startOfWeek;
	
	/**
	 * Instantiates a new attendance record export setting dto.
	 */
	public AttendanceRecordExportSettingDto() {
		super();
	}
}
