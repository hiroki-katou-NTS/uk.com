package nts.uk.screen.at.app.ksu003.start.dto;

import java.util.List;

import lombok.Value;

/**
 * 日別勤怠の休憩時間帯   (EA : List＜休憩時間帯＞＝勤務予定．休憩時間帯) 
 * @author phongtq
 *
 */
@Value
public class BreakTimeOfDailyAttdDto {
	//休憩種類 : 0: 就業時間帯から参照 (or 実績) and 1: スケジュールから参照 (or 予定)
	private int breakType; 
	//時間帯
	private List<BreakTimeZoneDto> breakTimeSheets;
}
