package nts.uk.ctx.at.request.app.find.dialog.superholiday;

import java.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcessCommon;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SixtyHourSettingOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SuperHolidayFinder.
 */
@Stateless
public class SuperHolidayFinder {

	@Inject
	private AbsenceTenProcessCommon absenceTenProcessCommon;

	@Inject
	private RemainNumberTempRequireService requireService;
	
	

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
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		String companyId = AppContexts.user().companyId();
		GeneralDate inputDate = GeneralDate.fromString(baseDate, "yyyyMMdd");
		OverTimeIndicationInformationDetails result = new OverTimeIndicationInformationDetails();

		// 10-5.60H超休の設定を取得する
		SixtyHourSettingOutput settingOutput = this.absenceTenProcessCommon.getSixtyHourSetting(companyId, employeeId, inputDate);

		// IF 取得した60H超休管理区分　＝＝　Trueの場合
		if (!settingOutput.isSixtyHourOvertimeMngDistinction()) {
			result.setPegManagementDtos(Collections.emptyList());
			result.setRemainNumberDetailDtos(Collections.emptyList());
			// 「60超過時間表示情報パラメータ」を返す
			return result;
		}

		// 期間中の60H超休残数を取得する
		// TODO

		// ドメインモデル「暫定60H超休管理データ」を取得

		// 社員に対応する締め期間を取得する
		DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cacheCarrier, employeeId, inputDate);

		// return 作成した60超過時間表示情報詳細を返す
		return result;
	}

	private DatePeriod getDatePeroid(GeneralDate startDate) {
		return new DatePeriod(startDate, startDate.addYears(1).addDays(-1));
	}
}
