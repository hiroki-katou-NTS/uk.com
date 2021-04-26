package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;

import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.NumberOfDaySuspensionDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.WorkInformationDto;

/**
 * 
 * @author tutt
 *
 */
public class WorkInfoOfDailyAttendanceDto {
	
	// 勤務実績の勤務情報
	private WorkInformationDto recordInfoDto;
	
	// 計算状態
	private int calculationState;
	
	// 直行区分
	private int goStraightAtr;
	
	// 直帰区分
	private int backStraightAtr;
	
	// 曜日
	private int dayOfWeek;
	
	// 始業終業時間帯
	private List<ScheduleTimeSheetDto> scheduleTimeSheets;
	
	// 振休振出として扱う日数
	private NumberOfDaySuspensionDto numberDaySuspension;
	
}