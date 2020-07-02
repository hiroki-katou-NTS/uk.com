package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;

/**
 * 勤務情報・勤務時間を用意する Output
 * @author phongtq
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrepareWorkOutput {
	
	/** 勤務情報 */
	private WorkInformation information;
	
	/** 勤務予定時間帯 */
	private List<TimezoneUse> scheduleTimeZone;
	
	/** 社員の短時間勤務 */
	private List<ShortWorkTimeDto> workTimeDto;
	
	/** スケジュール作成ログ＜Optional＞ */
	private Optional<ScheduleErrorLog> executionLog;
}
