package nts.uk.ctx.at.request.app.find.dialog.suspensionholiday;

import java.util.Optional;

import javax.ejb.Stateless;
/**
 * 社員の年休残数詳細情報を取得
 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL020_年休ダイアログ.アルゴリズム.社員の年休残数詳細情報を取得
 * @author Admin
 *
 */
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.InforAnnualHolidaysAccHolidayDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
@Stateless
public class GetInforNumberRemainEmployees {
	
	@Inject
	private RemainNumberTempRequireService requireService;
	
	@Inject
	private ReserveLeaveManagerApdater rsvLeaMngApdater;
	
	@Inject
	private CreateInforNumberRemainDays days;
	
	public InforAnnualHolidaysAccHolidayDto getInforNumberRemainEmployees(String cID, String sID) {
		val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();
		// 10-4.積立年休の設定を取得する
		boolean isRetentionManage = AbsenceTenProcess.getSetForYearlyReserved(require, cacheCarrier, cID, sID, GeneralDate.today());
		
		// 取得した積立年休管理区分をチェック
		if (isRetentionManage) {
			// 基準日時点の積立年休残数を取得する
			Optional<RsvLeaManagerImport> stock = rsvLeaMngApdater.getRsvLeaveManager(sID, GeneralDate.today());
			
			// 期間内の積立年休使用明細を取得する - todo
			
			// 積休残数詳細情報を作成 - todo
			InforAnnualHolidaysAccHolidayDto accHolidayDto = days.createInforNumberRemainDays();
			
		}
		return null;
	}
}
