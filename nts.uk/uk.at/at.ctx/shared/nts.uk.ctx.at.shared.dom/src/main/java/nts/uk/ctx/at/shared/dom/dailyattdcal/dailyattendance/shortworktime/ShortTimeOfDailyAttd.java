package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 日別勤怠の短時間勤務時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.短時間勤務.日別勤怠の短時間勤務時間帯
 * @author tutk
 *
 */
@Getter
public class ShortTimeOfDailyAttd implements DomainObject{
	/** 時間帯 */
	private List<ShortWorkingTimeSheet> shortWorkingTimeSheets;

	public ShortTimeOfDailyAttd(List<ShortWorkingTimeSheet> shortWorkingTimeSheets) {
		super();
		this.shortWorkingTimeSheets = shortWorkingTimeSheets;
	}

	public void setShortWorkingTimeSheets(List<ShortWorkingTimeSheet> shortWorkingTimeSheets) {
		this.shortWorkingTimeSheets = shortWorkingTimeSheets;
	}
	
}
