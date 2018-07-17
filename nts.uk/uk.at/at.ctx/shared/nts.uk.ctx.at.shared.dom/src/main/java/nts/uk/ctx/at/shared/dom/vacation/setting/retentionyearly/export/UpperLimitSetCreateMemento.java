package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSettingGetMemento;

/**
 * 作成：上限設定
 * @author shuichu_ishida
 */
public class UpperLimitSetCreateMemento implements UpperLimitSettingGetMemento {

	/** 保持年数 */
	private RetentionYearsAmount retentionYearsAmount;
	/** 上限日数 */
	private MaxDaysRetention maxDaysCumulation;
	
	/**
	 * コンストラクタ
	 * @param retentionYearsAmount 保持年数
	 * @param maxDaysCumulation 上限日数
	 */
	public UpperLimitSetCreateMemento(
			int retentionYearsAmount,
			int maxDaysCumulation){
		
		this.retentionYearsAmount = new RetentionYearsAmount(retentionYearsAmount);
		this.maxDaysCumulation = new MaxDaysRetention(maxDaysCumulation);
	}
	
	@Override
	public RetentionYearsAmount getRetentionYearsAmount() {
		return this.retentionYearsAmount;
	}
	
	@Override
	public MaxDaysRetention getMaxDaysCumulation() {
		return this.maxDaysCumulation;
	}
}
