package nts.uk.ctx.at.request.app.find.dialog.superholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SuperHolidayFinder.
 */
@Stateless
public class SuperHolidayFinder {

	/** The closure service. */
	@Inject
	private ClosureService closureService;

	/**
	 * KDL017
	 *  アルゴリズム「60H超休の表示」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public OverTimeIndicationInformationDetails getOverTimeIndicationInformationDetails(String employeeId,
			String baseDate) {
		String companyId = AppContexts.user().companyId();
		GeneralDate inputDate = GeneralDate.fromString(baseDate, "yyyyMMdd");

		// 10-5.60H超休の設定を取得する
		// TODO

		// 取得した60H超休管理区分 ＝＝ Trueの場合
		// TODO

		// 期間中の60H超休残数を取得する
		// TODO
		
		// ドメインモデル「暫定60H超休管理データ」を取得

		// 社員に対応する締め期間を取得する
		DatePeriod closingPeriod = this.closureService.findClosurePeriod(employeeId, inputDate);

		// 60超過時間表示情報詳細を作成
		OverTimeIndicationInformationDetails result = new OverTimeIndicationInformationDetails();
		
		// return 作成した60超過時間表示情報詳細を返す
		return result;
	}

}
