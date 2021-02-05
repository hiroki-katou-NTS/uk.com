package nts.uk.screen.at.app.command.kmk.kmk004.f;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.F：基本設定（通常勤務）.メニュー別OCD.職場別基本設定（通常勤務）を削除する
 * @author chungnt
 *
 */

@Stateless
public class DeleteSettingsForCompany {

	@Inject
	private RegularLaborTimeComRepo comRepo;

	@Inject
	private ComRegulaMonthActCalSetRepo actCalSetRepo;
	
	public void delete() {
		this.comRepo.remove(AppContexts.user().companyId());
		this.actCalSetRepo.remove(AppContexts.user().companyId());
	}
}
