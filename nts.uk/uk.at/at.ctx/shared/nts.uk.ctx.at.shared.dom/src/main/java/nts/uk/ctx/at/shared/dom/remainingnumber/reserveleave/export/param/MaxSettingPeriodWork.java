package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export.param;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 積立年休上限設定期間WORK
 * @author shuichu_ishida
 */
@Getter
public class MaxSettingPeriodWork {

	/** 期間 */
	private DatePeriod period;
	/** 上限設定 */
	private UpperLimitSetting maxSetting;
	
	/**
	 * コンストラクタ
	 */
	public MaxSettingPeriodWork(){
		
		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.maxSetting = null;
	}
	
	/**
	 * ファクトリー
	 * @param period 期間
	 * @param maxSetting 上限設定
	 * @return 積立年休上限設定期間WORK
	 */
	public static MaxSettingPeriodWork of(DatePeriod period, UpperLimitSetting maxSetting){
		
		MaxSettingPeriodWork domain = new MaxSettingPeriodWork();
		domain.period = period;
		domain.maxSetting = maxSetting;
		return domain;
	}
}
