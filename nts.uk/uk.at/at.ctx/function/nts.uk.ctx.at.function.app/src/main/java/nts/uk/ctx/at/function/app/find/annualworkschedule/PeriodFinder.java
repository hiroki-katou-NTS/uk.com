package nts.uk.ctx.at.function.app.find.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PeriodFinder {

	@Inject
	private AgreementOperationSettingAdapter agreementOperationSettingAdapter;
	@Inject
	private ClosureEmploymentService closureEmploymentService;

	public PeriodDto getPeriod() {
		//社員に対応する処理締めを取得する
		Closure closure
			= closureEmploymentService.findClosureByEmployee(AppContexts.user().employeeId(), GeneralDate.today()).get();
		YearMonth standardYm = closure.getClosureMonth().getProcessingYm();
		//基準月に対応する起算月を求める
		int startMonth = agreementOperationSettingAdapter.find().getStartingMonth() + 1; //month value: 0-11
		int startYear = standardYm.year() - (startMonth <= standardYm.month()? 0: 1);

		YearMonth startYm = YearMonth.of(startYear, startMonth);
		YearMonth endYm   = startYm.addMonths(11);
		return new PeriodDto(startYm.year() + "" + startYm.month(), endYm.year() + "" + endYm.month());
	}

}
