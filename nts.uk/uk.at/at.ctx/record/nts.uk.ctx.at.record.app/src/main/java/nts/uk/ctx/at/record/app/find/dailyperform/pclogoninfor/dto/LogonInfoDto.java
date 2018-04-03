package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 時間帯 */
@AllArgsConstructor
@NoArgsConstructor
public class LogonInfoDto {

	private Integer logNo;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "ログオン")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer logOn;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "ログオフ")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer logOff;
}
