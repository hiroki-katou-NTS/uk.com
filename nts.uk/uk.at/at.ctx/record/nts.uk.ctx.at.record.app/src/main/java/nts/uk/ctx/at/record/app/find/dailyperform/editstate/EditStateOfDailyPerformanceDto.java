package nts.uk.ctx.at.record.app.find.dailyperform.editstate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.ws.json.serializer.GeneralDateDeserializer;
import nts.arc.layer.ws.json.serializer.GeneralDateSerializer;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

@AttendanceItemRoot(rootName = "日別実績の編集状態")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditStateOfDailyPerformanceDto extends AttendanceItemCommon {

	// TODO: not map item id
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 勤怠項目ID: 勤怠項目ID */
	private int attendanceItemId;

	/** 処理年月日: 年月日 */
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate ymd;

	/** 編集状態: 日別実績の編集状態 */
	// @AttendanceItemLayout(layout = "A", jpPropertyName = "")
	// @AttendanceItemValue(type = ValueType.INTEGER)
	private int editStateSetting;

	public static EditStateOfDailyPerformanceDto getDto(EditStateOfDailyPerformance c) {
		if(c == null) {
			return new EditStateOfDailyPerformanceDto();
		}
		EditStateOfDailyPerformanceDto dto = new EditStateOfDailyPerformanceDto(c.getEmployeeId(), c.getAttendanceItemId(), c.getYmd(),
				c.getEditStateSetting() == null ? 0 : c.getEditStateSetting().value);
		dto.exsistData();
		return dto;
	}
	
	public static EditStateOfDailyPerformanceDto createWith(String employeeId, int itemId, GeneralDate ymd, int state) {
		EditStateOfDailyPerformanceDto dto = new EditStateOfDailyPerformanceDto(employeeId, itemId, ymd, state);
		dto.exsistData();
		return dto;
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public EditStateOfDailyPerformance toDomain(String employeeId, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new EditStateOfDailyPerformance(employeeId, attendanceItemId, date,
				ConvertHelper.getEnum(editStateSetting, EditStateSetting.class));
	}
}
