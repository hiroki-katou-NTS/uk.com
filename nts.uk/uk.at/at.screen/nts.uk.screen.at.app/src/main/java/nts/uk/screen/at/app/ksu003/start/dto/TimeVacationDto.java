package nts.uk.screen.at.app.ksu003.start.dto;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
/**
 * 時間休暇
 * @author phongtq
 *
 */
@Value
public class TimeVacationDto {
	//時間帯リスト
	private List<TimeSpanForCalcDto> timeZone;
	//使用時間
	private DailyAttdTimeVacationDto usageTime;
}
