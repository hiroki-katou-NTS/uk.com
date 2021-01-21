package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * @author ThanhNX
 *
 *         締め設定を取得する
 */
public class GetTightSetting {

	// 締め設定を取得する
	public static Optional<GetTightSettingResult> getTightSetting(Require require, String companyId, String employeeId,
			GeneralDate baseDate, ExpirationTime expirationTime, GeneralDate dateOccurrence) {

		// 「休暇使用期限」が当月と年度末クリアの場合のみ取得
		if (expirationTime != ExpirationTime.THIS_MONTH && expirationTime != ExpirationTime.END_OF_YEAR) {
			return Optional.empty();
		}

		// 社員に対応する処理締めを取得する
		val cacheCarrier = new CacheCarrier();
		Closure cls = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, baseDate);
		// 指定した年月日時点の締め期間を取得する
		Optional<ClosurePeriod> clsPeriod = cls.getClosurePeriodByYmd(dateOccurrence);

		if (!clsPeriod.isPresent())
			return Optional.empty();

		// 会社の期首月を取得する
		CompanyDto companyDto = require.getFirstMonth(companyId);

		return Optional.of(new GetTightSettingResult(companyDto.getStartMonth(), clsPeriod.get().getClosureDate(),
				clsPeriod.get().getPeriod()));

	}

	@AllArgsConstructor
	@Getter
	public static class GetTightSettingResult {

		// 期首月
		private int startMonth;

		/** 日付 */
		private ClosureDate closureDate;

		// 期間
		private DatePeriod period;

	}

	public static interface Require extends ClosureService.RequireM3{

		// ClosureService
		//Closure getClosureDataByEmployee(String employeeId, GeneralDate baseDate);

		// CompanyAdapter
		CompanyDto getFirstMonth(String companyId);

	}
}
