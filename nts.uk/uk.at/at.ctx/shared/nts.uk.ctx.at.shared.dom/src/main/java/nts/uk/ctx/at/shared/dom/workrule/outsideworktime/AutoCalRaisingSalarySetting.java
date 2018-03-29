package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Value;

/**
 * 加給の自動計算設定
 * @author keisuke_hoshina
 *
 */
@Value
public class AutoCalRaisingSalarySetting {
	private boolean specificRaisingSalaryCalcAtr;
	private boolean raisingSalaryCalcAtr;
}
