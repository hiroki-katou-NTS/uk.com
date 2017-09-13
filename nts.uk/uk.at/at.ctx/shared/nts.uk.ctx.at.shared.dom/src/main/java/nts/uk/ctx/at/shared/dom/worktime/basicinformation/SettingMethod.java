package nts.uk.ctx.at.shared.dom.worktime.basicinformation;

/**
 * 就業時間帯の設定方法
 * @author keisuke_hoshina
 *
 */
public enum SettingMethod {
	FixedWork,
	Jogging,
	FluidWork,
	OvertimeWork;
	
	/**
	 * 流動勤務か判定する
	 * @return　流動勤務である
	 */
	public boolean isFluidWork() {
		return FluidWork.equals(this);
	}
}
