package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment;

/**
 * 労働制
 * @author keisuke_hoshina
 *
 */
public enum WorkingSystem {
	RegularWork,
	FlexTimeWork,
	VariableWorkingTimeWork,
	ExcludedWorkingCalculate,;


	/**
	 * 労働制が就業計算対象外か判定
	 * @return　就業計算対象外である
	 */
	public boolean isExcludedWorkingCalculate() {
		return this.equals(ExcludedWorkingCalculate);
	}
	
	public boolean isVariableWorkingTimeWork() {
		return this.equals(VariableWorkingTimeWork);
	}
}
