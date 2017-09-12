package nts.uk.ctx.at.shared.dom.worktime.basicinformation;

import lombok.Value;

/**
 * 就業時間帯勤務区分
 * @author keisuke_hoshina
 *
 */
@Value
public class WorkTimeClassification {
	private DailyWorkClassification dailyWorkClassification;
	private SettingMethod settingMethod;
	
	/**
	 * フレックス勤務かつ流動勤務であるかを判定する
	 * @return　フレックスかつ流動勤務である
	 */
	public boolean isfluidFlex() {
		return settingMethod.isFluidWork()&&dailyWorkClassification.isFlexWork();
	}
}
