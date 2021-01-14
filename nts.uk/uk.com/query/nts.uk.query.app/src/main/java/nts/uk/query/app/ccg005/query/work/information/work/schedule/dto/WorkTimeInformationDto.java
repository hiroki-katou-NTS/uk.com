package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;

@Builder
@Data
public class WorkTimeInformationDto {
	// 時刻変更理由
	private ReasonTimeChange reasonTimeChange;

	// 時刻
	private Integer timeWithDay;
}
