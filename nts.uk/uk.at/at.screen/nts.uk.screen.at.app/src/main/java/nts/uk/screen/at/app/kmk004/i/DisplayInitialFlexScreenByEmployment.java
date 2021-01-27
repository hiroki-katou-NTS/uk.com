package nts.uk.screen.at.app.kmk004.i;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用別法定労働時間の登録（フレックス勤務）の初期画面を表示する
 */
@Stateless
public class DisplayInitialFlexScreenByEmployment {

	/*
	 * @Inject private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepo;
	 */

	@Inject
	private EmploymentList employmentList;

	@Inject
	private SelectEmploymentFlex selectEmploymentFlex;

	public DisplayInitialFlexScreenByEmploymentDto displayInitialFlexScreenByEmployment(String empCd) {

		DisplayInitialFlexScreenByEmploymentDto result = new DisplayInitialFlexScreenByEmploymentDto();

		// 1.会社別フレックス勤務集計方法

		/*this.getFlexPredWorkTimeRepo.find(comId).ifPresent(x -> {
			result.getFlexBasicSetting().setFlexPredWorkTime(GetFlexPredWorkTimeDto.fromDomain(x));
		});*/

		// 職場リストを表示する
		// input： 勤務区分 ← 2：フレックス勤務
		result.setAlreadySettings(this.employmentList.get(LaborWorkTypeAttr.FLEX).stream().map(x -> x.employmentCode)
				.collect(Collectors.toList()));

		// 雇用を選択する（フレックス勤務）
		// input：
		// 雇用コード ← 雇用リストの先頭の雇用コード
		// 勤務区分 ← 2：フレックス勤務
		SelectEmploymentFlexDto selectDto = this.selectEmploymentFlex
				.selectEmploymentFlex(empCd);

		result.setFlexBasicSetting(selectDto.getFlexBasicSetting());

		result.setYearList(selectDto.getYearList());

		return result;
	}

}
