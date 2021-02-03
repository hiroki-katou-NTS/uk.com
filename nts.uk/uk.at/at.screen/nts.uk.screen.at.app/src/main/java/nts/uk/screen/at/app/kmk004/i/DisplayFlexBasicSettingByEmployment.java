package nts.uk.screen.at.app.kmk004.i;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.screen.at.app.kmk004.g.ComFlexMonthActCalSetDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用別基本設定（フレックス勤務）を表示する.雇用別基本設定（フレックス勤務）を表示する
 */
@Stateless
public class DisplayFlexBasicSettingByEmployment {

	@Inject
	private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;

	@Inject
	private ComFlexMonthActCalSetRepo comFlexRepo;

	public DisplayFlexBasicSettingByEmploymentDto displayFlexBasicSettingByEmployment(String employmentCd) {

		DisplayFlexBasicSettingByEmploymentDto result = new DisplayFlexBasicSettingByEmploymentDto();
		// 1. get(ログイン会社ID,雇用コード)
		// 雇用別フレックス勤務集計方法
		Optional<EmpFlexMonthActCalSet> empFlexOpt = this.empFlexMonthActCalSetRepo.find(AppContexts.user().companyId(),
				employmentCd);

		if (empFlexOpt.isPresent()) {

			result.setFlexMonthActCalSet(EmpFlexMonthActCalSetDto.fromDomain(empFlexOpt.get()));
		}

		this.comFlexRepo.find(AppContexts.user().companyId()).ifPresent(x -> {
			result.setComFlexMonthActCalSet(ComFlexMonthActCalSetDto.fromDomain(x));
		});

		return result;

	}
}
