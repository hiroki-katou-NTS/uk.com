package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktimeset.common.CommonRestSetting;

/**
 * 固定勤務の休憩設定
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class FixRestSetting {
	//共通の休憩設定
	private CommonRestSetting commonSet;
	//計算方法
	private FixRestCalcMethod fixRestCalcMethod;
	
	/*:
	 * 固定休憩の計算方法を「予定を参照する」に変更
	 */
	public void changeCalcMethodToSchedule() {
		this.fixRestCalcMethod = FixRestCalcMethod.ReferToSchedule;
	}
}
