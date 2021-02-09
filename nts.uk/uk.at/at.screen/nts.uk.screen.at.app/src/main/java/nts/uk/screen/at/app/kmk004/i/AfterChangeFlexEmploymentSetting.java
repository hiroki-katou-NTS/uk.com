package nts.uk.screen.at.app.kmk004.i;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.kmk004.g.ComFlexMonthActCalSetDto;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用別基本設定（フレックス勤務）を作成・変更・削除した時
 */
@Stateless
public class AfterChangeFlexEmploymentSetting {

	@Inject
	private DisplayFlexBasicSettingByEmployment displayFlexBasicSettingByEmployment;

	@Inject
	private EmploymentList employmentList;
	
	@Inject
	private ComFlexMonthActCalSetRepo comFlexRepo;
	public AfterChangeFlexEmploymentSettingDto afterChangeFlexEmploymentSetting(String employmentCd) {
		AfterChangeFlexEmploymentSettingDto result = new AfterChangeFlexEmploymentSettingDto();
		// 雇用別基本設定（フレックス勤務）を表示する
		DisplayFlexBasicSettingByEmploymentDto displayFlexDto = this.displayFlexBasicSettingByEmployment
				.displayFlexBasicSettingByEmployment(employmentCd);

		result.setFlexMonthActCalSet(displayFlexDto.getFlexMonthActCalSet());
		// 雇用リストを表示する
		result.setAlreadySettings(this.employmentList.get(LaborWorkTypeAttr.FLEX).stream().map(x -> x.employmentCode)
				.collect(Collectors.toList()));
		
		this.comFlexRepo.find(AppContexts.user().companyId()).ifPresent(x -> {
			result.setComFlexMonthActCalSet(ComFlexMonthActCalSetDto.fromDomain(x));
		});

		return result;
	}
}
