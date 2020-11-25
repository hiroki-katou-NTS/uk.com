package nts.uk.screen.at.app.find.ktg.ktg005.a;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KTG_ウィジェット.KTG005_申請件数.ユースケース.最新情報を表示する.最新情報を表示する
 */
@Stateless
public class DisplayNewestInfo {

	@Inject
	private ApplicationRepository appRepo;

	/**
	 * 申請件数最新表示
	 * 
	 * @param companyId
	 * @param period
	 * @param employeeId
	 * @return
	 */
	public NumberOfAppDto displayNewestInfo(String companyId, DatePeriod period, String employeeId) {
		// 申請件数取得
		return GetNumberOfApps.getNumberOfApps(GetNumberOfApps.createRequire(appRepo), companyId, period, employeeId);
	}
}
