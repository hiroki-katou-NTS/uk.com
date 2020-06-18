package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting;

import java.util.List;

import lombok.Getter;

/**
 * 日別勤怠の外出時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.休憩・外出.日別勤怠の外出時間帯
 * @author tutk
 *
 */
@Getter
public class OutingTimeOfDailyAttd {
	//時間帯
	private List<OutingTimeSheet> outingTimeSheets;

	public OutingTimeOfDailyAttd(List<OutingTimeSheet> outingTimeSheets) {
		super();
		this.outingTimeSheets = outingTimeSheets;
	}
	
}
