package nts.uk.screen.at.app.ksus02;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.TimeZoneDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

/**
 * 勤務情報と補正済み所定時間帯 Dto
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkInfoAndScheTime {

	// 勤務種類
	private WorkTypeDto workType;
	// 就業時間帯
	private WorkTimeSettingDto workTimeSettingDto;
	// 補正済み所定時間帯
	private List<TimeZoneDto> timeZones;

	public WorkInfoAndScheTime(WorkTypeDto workType, WorkTimeSettingDto workTimeSettingDto,
			List<TimeZoneDto> timeZones) {
		super();
		this.workType = workType;
		this.workTimeSettingDto = workTimeSettingDto;
		this.timeZones = timeZones;
	}
}
