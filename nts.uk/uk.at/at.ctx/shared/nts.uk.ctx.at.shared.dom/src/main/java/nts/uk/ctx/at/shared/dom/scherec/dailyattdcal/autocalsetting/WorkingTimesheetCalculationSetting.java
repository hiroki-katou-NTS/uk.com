package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting;

/**
 * 就業時間帯自動計算区分
 * @author keisuke_hoshina
 *
 */
public enum WorkingTimesheetCalculationSetting {
	NotAutoCalculation,
	CalculateAutomatic;
	
	/**
	 * 自動計算であるか判定する
	 * @return 自動計算する
	 */
	public boolean isCalculateAutomatic() {
		return CalculateAutomatic.equals(this);
	}
}
