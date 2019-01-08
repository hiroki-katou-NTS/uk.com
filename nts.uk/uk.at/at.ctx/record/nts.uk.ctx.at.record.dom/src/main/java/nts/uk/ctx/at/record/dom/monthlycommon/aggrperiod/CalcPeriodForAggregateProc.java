package nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 処理：集計すべき期間を計算
 * @author shuichu_ishida
 */
public class CalcPeriodForAggregateProc {

	/** 所属雇用履歴の取得 */
	private EmploymentHistAdapter employmentHistAdapter;
	/** 雇用に紐づく就業締めの取得 */
	private ClosureEmploymentRepository closureEmploymentRepository;
	/** 締めの取得 */
	private ClosureRepository closureRepository;
	/** 当月の期間を算出する */
	private ClosureService closureService;
	
	/** 締め処理期間リスト */
	private List<ClosurePeriod> closurePeriods;
	/** 締めID履歴リスト */
	private List<ClosureIdHistory> closureIdHistories;
	/** 実締め毎集計期間リスト */
	private List<AggrPeriodEachActualClosure> aggrPeriods;

	public CalcPeriodForAggregateProc(
			EmploymentHistAdapter employmentHistAdapter,
			ClosureEmploymentRepository closureEmploymentRepository,
			ClosureRepository closureRepository,
			ClosureService closureService){
	
		this.employmentHistAdapter = employmentHistAdapter;
		this.closureEmploymentRepository = closureEmploymentRepository;
		this.closureRepository = closureRepository;
		this.closureService = closureService;
	}
	
	/**
	 * 集計すべき期間を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrEnd 集計終了日
	 */
	public List<ClosurePeriod> algorithm(String companyId, String employeeId, GeneralDate aggrEnd){
	
		this.closurePeriods = new ArrayList<>();
		this.closureIdHistories = new ArrayList<>();
		this.aggrPeriods = new ArrayList<>();
		
		// 締められていない期間の締めIDを取得
		this.getClosureIdOfNotClosurePeriod(companyId, employeeId);

		// 締めID履歴の先頭の開始日と「集計終了日」を比較　→　検索終了日
		GeneralDate findEnd = aggrEnd;	// 検索終了日
		if (!this.closureIdHistories.isEmpty()){
			GeneralDate checkStart = this.closureIdHistories.get(0).getPeriod().start();
			if (checkStart.after(aggrEnd)) findEnd = checkStart;
		}
		
		for (val closureIdHistory: this.closureIdHistories){
			
			// 締めID履歴の期間内で締め処理期間を作成
			this.createAggrPeriodEachActualClosureWithinHistories(companyId, closureIdHistory, findEnd.addMonths(1));
		}
		
		for (val aggrPeriod : this.aggrPeriods){
			
			// 締め処理期間を作成
			this.createClosurePeriod(aggrPeriod, aggrEnd);
		}
		
		if (!this.closurePeriods.isEmpty()){

			// 締め処理期間の先頭の年月を取得
			YearMonth firstYm = this.closurePeriods.get(0).getYearMonth();
			
			// 締め処理期間から、集計終了日以降の「締め処理期間」を削除
			ListIterator<ClosurePeriod> itrClosurePeriods = this.closurePeriods.listIterator();
			while (itrClosurePeriods.hasNext()){
				ClosurePeriod target = itrClosurePeriods.next();
				if (!target.getYearMonth().equals(firstYm)){
					if (target.getAggrPeriods().isEmpty()){
						itrClosurePeriods.remove();
					}
					else if (target.getAggrPeriods().get(0).getPeriod().start().after(aggrEnd)){
						itrClosurePeriods.remove();
					}
				}
			}
		}
		
		return this.closurePeriods;
	}
	
	/**
	 * 締められていない期間の締めIDを取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 */
	private void getClosureIdOfNotClosurePeriod(String companyId, String employeeId){
		
		// 「所属雇用履歴」を取得する
		val employmentHists = this.employmentHistAdapter.findByEmployeeIdOrderByStartDate(employeeId);
		
		for (val employmentHist : employmentHists){
			val employmentCd = employmentHist.getEmploymentCode();
			
			// 締めIDを取得する
			val closureEmploymentOpt = this.closureEmploymentRepository.findByEmploymentCD(companyId, employmentCd);
			if (!closureEmploymentOpt.isPresent()) continue;
			val closureEmployment = closureEmploymentOpt.get();
			
			// 「締め」を取得
			val closureId = closureEmployment.getClosureId();
			val closureOpt = this.closureRepository.findById(companyId, closureId);
			if (!closureOpt.isPresent()) continue;
			val closure = closureOpt.get();
			if (closure.getUseClassification() != UseClassification.UseClass_Use) continue;
			
			// 当月の期間を算出する
			val processingYm = closure.getClosureMonth().getProcessingYm();
			val closurePeriod = this.closureService.getClosurePeriod(closureId, processingYm);
			
			// 「所属雇用履歴」が「締め期間」より前がチェック
			val employmentPeriod = employmentHist.getPeriod();
			if (employmentPeriod.end().before(closurePeriod.start())) continue;
			
			// 「締めID履歴リスト」の末尾と締めIDを比較
			if (this.closureIdHistories.size() > 0){
				val lastHistory = this.closureIdHistories.get(this.closureIdHistories.size() - 1);
				if (lastHistory.getClosureId().value == closureId.intValue()){
					
					// 「締めID履歴リスト」の末尾を更新
					lastHistory.setPeriod(new DatePeriod(
							lastHistory.getPeriod().start(), employmentPeriod.end()));
					continue;
				}
			}
			
			// 処理中の「所属雇用履歴」より前が締められたかチェックし、「締めID履歴」を作成
			if (employmentPeriod.start().after(closurePeriod.start())){
				this.closureIdHistories.add(ClosureIdHistory.of(closureId, employmentPeriod));
			}
			else {
				this.closureIdHistories.add(ClosureIdHistory.of(closureId,
						new DatePeriod(closurePeriod.start(), employmentPeriod.end())));
			}
		}
	}
	
	/**
	 * 締めID履歴の期間内で締め処理期間を作成
	 * @param companyId 会社ID
	 * @param closureIdHistory 締めID履歴
	 * @param aggrEnd 集計終了日
	 */
	private void createAggrPeriodEachActualClosureWithinHistories(
			String companyId, ClosureIdHistory closureIdHistory, GeneralDate aggrEnd){
	
		val closureId = closureIdHistory.getClosureId();
		val histPeriod = closureIdHistory.getPeriod();
		
		// 集計開始日を取得
		GeneralDate aggrStart = histPeriod.start();
		
		// 「締め」を取得
		val closureOpt = this.closureRepository.findById(companyId, closureId.value);
		if (!closureOpt.isPresent()) return;
		val closure = closureOpt.get();
		if (closure.getUseClassification() != UseClassification.UseClass_Use) return;
		
		while (true){
		
			// 指定した年月日時点の締め期間を取得する
			val closurePeriodOpt = closure.getClosurePeriodByYmd(aggrStart);
			if (!closurePeriodOpt.isPresent()) return;
			val closurePeriod = closurePeriodOpt.get();
			
			// 集計対象期間を作成する
			DatePeriod aggrTargetPeriod = new DatePeriod(aggrStart, closurePeriod.getPeriod().end());
			
			// 「締めID履歴．期間」と「集計対象期間」の重複を計算
			if (histPeriod.isBefore(aggrTargetPeriod)) break;
			if (histPeriod.isAfter(aggrTargetPeriod)) break;
			if (aggrTargetPeriod.contains(histPeriod.start())){
				aggrTargetPeriod = aggrTargetPeriod.cutOffWithNewStart(histPeriod.start());
			}
			if (aggrTargetPeriod.contains(histPeriod.end())){
				aggrTargetPeriod = aggrTargetPeriod.cutOffWithNewEnd(histPeriod.end());
			}
			
			// 「実締め毎集計期間」を作成
			this.aggrPeriods.add(AggrPeriodEachActualClosure.of(closureId, closurePeriod.getClosureDate(),
					closurePeriod.getYearMonth(), aggrTargetPeriod, closurePeriod.getPeriod()));
			
			// 「締めID履歴．終了日」と「対象集計期間．終了日」を比較
			if (histPeriod.end().beforeOrEquals(aggrTargetPeriod.end())) break;
			
			// 「集計終了日」と「対象集計期間．終了日」を比較
			if (aggrEnd.beforeOrEquals(aggrTargetPeriod.end())) break;
			
			// 集計開始日←「対象集計期間．終了日」の翌日
			aggrStart = aggrTargetPeriod.end().addDays(1);
		}
	}
	
	/**
	 * 締め処理期間を作成
	 * @param aggrPeriod 実締め毎集計期間
	 * @param aggrEnd 集計終了日
	 */
	private void createClosurePeriod(AggrPeriodEachActualClosure aggrPeriod, GeneralDate aggrEnd){
		
		// 「締め処理期間リスト」の末尾を確認
		if (this.closurePeriods.size() > 0) {
			val lastPeriod = this.closurePeriods.get(this.closurePeriods.size() - 1);
			
			// 既存の締め処理期間に追加するかチェック
			if (lastPeriod.getYearMonth().equals(aggrPeriod.getYearMonth()) &&
				lastPeriod.getClosureYmd().afterOrEquals(aggrPeriod.getOriginalClosurePeriod().end())){
				
				// 末尾の締め処理期間に実締め毎集計期間を追加
				lastPeriod.addAggrPeriodEachActualClosure(aggrPeriod);
				return;
			}
		}
		
		// 締め処理期間を新規作成し、リストに追加
		this.closurePeriods.add(ClosurePeriod.of(aggrPeriod));
	}
}
