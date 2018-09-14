package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Data;

/**
 * 加給の自動計算設定
 * @author keisuke_hoshina
 *
 */
@Data
public class AutoCalRaisingSalarySetting {
	private boolean specificRaisingSalaryCalcAtr;
	private boolean raisingSalaryCalcAtr;
	
	public AutoCalRaisingSalarySetting(boolean specificRaisingSalaryCalcAtr, boolean raisingSalaryCalcAtr) {
		super();
		this.specificRaisingSalaryCalcAtr = specificRaisingSalaryCalcAtr;
		this.raisingSalaryCalcAtr = raisingSalaryCalcAtr;
	}
	
	public static AutoCalRaisingSalarySetting defaultValue(){
		return new AutoCalRaisingSalarySetting(false, false);
	}
}
