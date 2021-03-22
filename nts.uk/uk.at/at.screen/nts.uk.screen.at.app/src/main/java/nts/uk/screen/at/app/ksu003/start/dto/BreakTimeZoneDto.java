package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;

/**
 * 時間帯
 * @author phongtq
 *
 */

@Value
public class BreakTimeZoneDto {
	//休憩枠NO
	private int breakFrameNo;
	//開始 - 勤怠打刻(実打刻付き)
	private int startTime; 
	//終了 - 勤怠打刻(実打刻付き)
	private int endTime; 
	//休憩時間: 勤怠時間 
	private int breakTime;
}
