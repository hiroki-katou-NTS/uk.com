package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PeriodFinder {
	@Inject
	private ClosureEmploymentService closureEmploymentService;
	@Inject
	private CompanyAdapter companyAdapter;

	public PeriodDto getPeriod() {
		// 社員に対応する処理締めを取得する
		Optional<Closure> closureOtp = closureEmploymentService.findClosureByEmployee(AppContexts.user().employeeId(),
				GeneralDate.today());
		if (!closureOtp.isPresent()) {
			return null;
		}
		YearMonth standardYm = closureOtp.get().getClosureMonth().getProcessingYm();
		// 会社の期首月を取得する (RequetsList108)
		CompanyDto companyDto = companyAdapter.getFirstMonth(AppContexts.user().companyId());
		int startMonth = companyDto.getStartMonth();
		// 起算月 ＜＝ パラメータ「基準年月．月」
		int startYear = standardYm.year() - (startMonth <= standardYm.month() ? 0 : 1);

		YearMonth startYm = YearMonth.of(startYear, startMonth);
		// 取得した起算年月の12ヶ月先の年月を取得する
		YearMonth endYm = startYm.addMonths(11);
		return new PeriodDto(startYm.year() + "" + startYm.month(), endYm.year() + "" + endYm.month());
	}
	
	public YearMonth getCurentMonth() {
		return closureEmploymentService.getCurentMonth();
	}
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.会社.Export.会社の期首月を取得する.アルゴリズム.会社の期首月を取得する
	 * @return
	 */
	public String getStartMonth() {
		// ドメインモデル「会社情報」を取得する
		Optional<CompanyImport622> optCompany = this.companyAdapter.getCompanyNotAbolitionByCid(AppContexts.user().companyId());
		return optCompany.map(data -> StringUtil.padLeft(String.valueOf(data.getStartMonth()), 2, '0')).orElse(null);
	}
}
