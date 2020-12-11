package nts.uk.screen.at.app.kmk004.k;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.screen.at.app.kmk004.i.EmpFlexMonthActCalSetDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.K：基本設定（フレックス勤務）.メニュー別OCD.雇用別基本設定（フレックス勤務）を初期画面を表示する
 */
@Stateless
public class DisplayInitialFlexBasicSettingByEmployment {

	@Inject
	private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;

	public EmpFlexMonthActCalSetDto displayInitialFlexBasicSettingByEmployment(String employmentCd) {

		// 雇用別フレックス勤務集計方法
		Optional<EmpFlexMonthActCalSet> empFlexOpt = this.empFlexMonthActCalSetRepo.find(AppContexts.user().companyId(),
				employmentCd);

		if (empFlexOpt.isPresent()) {
			return EmpFlexMonthActCalSetDto.fromDomain(empFlexOpt.get());
		}

		return null;

	}

}
