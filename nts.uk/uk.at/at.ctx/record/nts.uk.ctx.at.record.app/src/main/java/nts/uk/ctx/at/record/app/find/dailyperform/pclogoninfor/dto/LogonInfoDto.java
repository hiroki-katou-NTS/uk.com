package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 時間帯 */
@AllArgsConstructor
@NoArgsConstructor
public class LogonInfoDto implements ItemConst {

	private Integer no;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = LOGON)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer logOn;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LOGOFF)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer logOff;
	
	@Override
	public LogonInfoDto clone() {
		return new LogonInfoDto(no, logOn, logOff);
	}
}
