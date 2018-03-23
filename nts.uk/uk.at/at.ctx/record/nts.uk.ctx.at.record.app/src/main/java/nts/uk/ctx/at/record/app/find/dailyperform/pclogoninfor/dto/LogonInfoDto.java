package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/**　時間帯　*/
@AllArgsConstructor
@NoArgsConstructor
public class LogonInfoDto {

	private Integer logNo;

	@AttendanceItemLayout(layout="A", jpPropertyName="ログオン")
	private Integer logOn;

	@AttendanceItemLayout(layout="B", jpPropertyName="ログオフ")
	private Integer logOff;
}
