package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;

/** 時間設定 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSetting {
	
	/** 一定時間 **/
	private TimeOfDay  certainPeriodofTime;
	
	/**指定時間 **/
	private DesignatedTime designatedTime;
	
	/** 時間区分 **/
	private EnumTimeDivision enumTimeDivision;
	
	
}
