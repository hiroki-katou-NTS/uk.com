package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;

/** 日別実績の応援作業時間 */
@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_SUPPORT_TIME_NAME)
public class OuenWorkTimeOfDailyDto extends AttendanceItemCommon {
	/***/
	private static final long serialVersionUID = 1L;
	
	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate date;

	/** 日別勤怠の応援作業時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME,
			listMaxLength = 20, indexField = DEFAULT_INDEX_FIELD_NAME)
	private OuenWorkTimeOfDailyAttendanceDto workTimes;
	
	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.date;
	}
	
	public static OuenWorkTimeOfDailyDto getDto(String employeeId, GeneralDate date, OuenWorkTimeOfDailyAttendance workTimes) {
		OuenWorkTimeOfDailyDto dto = new OuenWorkTimeOfDailyDto();
		if(workTimes != null ) {
			dto.setEmployeeId(employeeId);
			dto.setDate(date);
			dto.setWorkTimes(OuenWorkTimeOfDailyAttendanceDto.valueOf(workTimes));
		}
		return dto;
	}
	
	@Override
	public OuenWorkTimeOfDaily toDomain(String employeeId, GeneralDate date) {
		return OuenWorkTimeOfDaily.create(employeeId, date, workTimes.toDomain());
	}
}
