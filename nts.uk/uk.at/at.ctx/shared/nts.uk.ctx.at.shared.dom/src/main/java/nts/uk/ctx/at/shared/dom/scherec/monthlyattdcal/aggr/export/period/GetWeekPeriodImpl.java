package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.period;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.GetWeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 週集計期間を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetWeekPeriodImpl implements GetWeekPeriod {

	/** 週開始の取得 */
	@Inject
	private GetWeekStart getWeekStart;
	/** 締めの取得 */
	@Inject
	private ClosureRepository closureRepo;
	
	/**
	 * 処理期間の各週の開始日、終了日を判断
	 * @return 期間リスト（期間順）
	 */
	@Override
	public List<DatePeriod> algorithm(String companyId, String employmentCd, String employeeId,
			DatePeriod period, WorkingSystem workingSystem, ClosureId closureId){
		
		List<DatePeriod> results = new ArrayList<>();
		
		// 不正チェック
		if (period.start().before(GeneralDate.min().addDays(7))) return results;
		
		// 週開始を取得する
		val weekStartOpt = this.getWeekStart.algorithm(companyId);
		if (!weekStartOpt.isPresent()) return results;
		val weekStart = weekStartOpt.get();
		
		// 締めを取得する
		val closureOpt = this.closureRepo.findById(companyId, closureId.value);
		if (!closureOpt.isPresent()) return results;
		val closure = closureOpt.get();
		
		// 週開始曜日を求める
		int aggregateWeek = 0;
		switch (weekStart){
		case Monday:
			aggregateWeek = 1;
			break;
		case Tuesday:
			aggregateWeek = 2;
			break;
		case Wednesday:
			aggregateWeek = 3;
			break;
		case Thursday:
			aggregateWeek = 4;
			break;
		case Friday:
			aggregateWeek = 5;
			break;
		case Saturday:
			aggregateWeek = 6;
			break;
		case Sunday:
			aggregateWeek = 7;
			break;
		case TighteningStartDate:
			
			// 締め開始日を取得する
			GeneralDate closureDate = period.start();
			val closurePeriodOpt = closure.getClosurePeriodByYmd(period.start());
			if (closurePeriodOpt.isPresent()){
				closureDate = closurePeriodOpt.get().getPeriod().start();
			}
			
			// 締め開始日の曜日を週開始曜日とする
			aggregateWeek = closureDate.dayOfWeek();
			break;
		}
		
		// 週集計開始日を求める　（集計開始日以前の直近の週開始曜日と同じ日）
		int periodStartWeek = period.start().dayOfWeek() - 1 + 7;	// 集計開始日曜日(0～6 + 7)
		int diffDays = periodStartWeek - (aggregateWeek - 1);		// 週集計開始曜日との日数差
		if (diffDays >= 7) diffDays -= 7;
		GeneralDate weekStartDate = period.start().addDays(-diffDays);	// 週集計開始日
		
		// 期間リストを作成する
		for ( ; weekStartDate.beforeOrEquals(period.end()); weekStartDate = weekStartDate.addDays(7)){
			GeneralDate startDate = weekStartDate;
			GeneralDate endDate = weekStartDate.addDays(6);
			if (endDate.after(period.end())) continue;
			results.add(new DatePeriod(startDate, endDate));
		}

		// 期間リストを返す
		return results;
	}
}
