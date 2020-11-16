package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/** 代休発生に必要な休日出勤時間 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HolidayWorkHourRequired {
	//使用区分 
	private boolean UseAtr;
	//時間設定
	private TimeSetting timeSetting;
}
