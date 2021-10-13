package nts.uk.ctx.at.shared.dom.employmentrules.workclosuredate;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 月別実績集計期間を算出する
 * 
 * @author tutk
 *
 */
@Stateless
public class CalculateMonthlyPeriodDefault implements CalculateMonthlyPeriodService {

	@Inject
	private ClosureRepository closureRepository;
	@Inject
	private ClosureEmploymentRepository closureEmpRepo;
	
	@Override
	public DatePeriod calculateMonthlyPeriod(Integer closureId, GeneralDate baseDate) {
		boolean checkOut = true;
		String companyId = AppContexts.user().companyId();
		//ドメイン「締め」を取得する
		Optional<Closure> closure = closureRepository.findById(companyId, closureId);
		YearMonth yearmonth = closure.get().getClosureMonth().getProcessingYm();
		DatePeriod datePeriodResult = ClosureService.getClosurePeriod(closureId, yearmonth, closure);
		while (checkOut) {
			// アルゴリズム「当月の期間を算出する」を実行する
			DatePeriod datePeriod = ClosureService.getClosurePeriod(createRequireM1(), closureId, yearmonth);
			// 締め期間．開始年月日を月別実績集計期間開始年月日に設定する
			// 締め期間に基準日が含まれているかチェックする
			if (datePeriod.start().beforeOrEquals(baseDate) && baseDate.beforeOrEquals(datePeriod.end())) {
				return datePeriod.newSpan(datePeriodResult.start(), datePeriod.end());
			}else {
				//基準日と締め期間．開始年月日を比較する
				if(baseDate.before(datePeriod.start())) {
					//DatePeriod result = datePeriod.newSpan(datePeriodResult.start(), datePeriod.end());
					return datePeriod.newSpan(datePeriodResult.start(), datePeriod.end());
				}else {
					yearmonth = yearmonth.addMonths(1);
				}
			}
		}
		return datePeriodResult;
	}

	private ClosureService.RequireM1 createRequireM1() {
		
		return new ClosureService.RequireM1() {
			
			@Override
			public Optional<Closure> closure(String companyId, int closureId) {

				return closureRepository.findById(companyId, closureId);
			}
			
			@Override
			public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
				return closureEmpRepo.findByEmploymentCD(companyID, employmentCD);
			}

			@Override
			public List<Closure> closureClones(String companyId, List<Integer> closureId) {
				return closureRepository.findByListId(companyId, closureId);
			}

			@Override
			public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
				return closureEmpRepo.findListEmployment(companyID, employmentCD);
			}
		};
	}
}
