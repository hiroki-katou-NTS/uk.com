package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.List;

import nts.uk.ctx.at.request.app.find.application.overtime.ApplicationTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOvertimeDetailDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.WorkInformationDto;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;

public class AppOverTimeCommand {
	// 残業区分
	public Integer overTimeClf;
	
	// 申請時間
	public ApplicationTimeDto applicationTime;
	
	// 休憩時間帯
	public List<TimeZoneWithWorkNoDto> breakTimeOp;
	
	// 勤務時間帯
	public List<TimeZoneWithWorkNoDto> workHoursOp;
	
	// 勤務情報
	public WorkInformationDto workInfoOp;
	
	// 時間外時間の詳細
	public AppOvertimeDetailDto detailOverTimeOp;
}
