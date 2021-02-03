package nts.uk.screen.at.app.kmk004.n;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.N：雇用別法定労働時間の登録（変形労働）.メニュー別OCD.雇用別法定労働時間の登録（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayInitialDeforScreenByEmployment {

	@Inject
	private EmploymentList employmentList;

	@Inject
	private SelectEmploymentDefor selectEmploymentDefor;

	public DisplayInitialDeforScreenByEmploymentDto displayInitialDeforScreenByEmployment() {

		DisplayInitialDeforScreenByEmploymentDto result = new DisplayInitialDeforScreenByEmploymentDto();

		// 1. 雇用リストを表示する
		// input：
		// 勤務区分＝1：変形労働
		result.setEmploymentCds(this.employmentList.get(LaborWorkTypeAttr.DEFOR_LABOR));

		// 2. 雇用を選択する（変形労働）
		// 雇用コード ← 雇用リストの先頭の雇用コード
		// 勤務区分 ← 1：変形労働

		if (!result.getEmploymentCds().isEmpty()) {
			result.setSelectEmploymentDeforDto(
					this.selectEmploymentDefor.selectEmploymentDefor(result.getEmploymentCds().get(0).employmentCode));
		}

		return result;
	}

}
