package nts.uk.screen.at.app.kmk004.i;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用別月単位労働時間（フレックス勤務）を複写した時
 */
@Stateless
public class AfterCopyFlexMonthlyWorkTimeSetEmp {

	@Inject
	private EmploymentList employmentList;

	public List<String> afterCopyFlexMonthlyWorkTimeSetEmp() {
		// 雇用リストを表示する
		return this.employmentList.get(LaborWorkTypeAttr.FLEX).stream().map(x -> x.employmentCode)
				.collect(Collectors.toList());
	}
}
