package nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 集計期間を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetClosurePeriodImpl implements GetClosurePeriod {

	/** 所属雇用履歴の取得 */
	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;
	/** 雇用に紐づく就業締めの取得 */
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	/** 締めの取得 */
	@Inject
	private ClosureRepository closureRepository;
	/** 当月の期間を算出する */
	@Inject
	private ClosureService closureService;
	
	/** 締め処理期間 */
	private ClosurePeriod closurePeriod;
	/** 締めID履歴リスト */
	private List<ClosureIdHistory> closureIdHistories;
	/** 実締め毎集計期間リスト */
	private List<AggrPeriodEachActualClosure> aggrPeriods;
	
	/** 集計期間を取得する */
	@Override
	public ClosurePeriod get(String companyId, String employeeId, GeneralDate criteriaDate,
			Optional<YearMonth> yearMonthOpt, Optional<ClosureId> closureIdOpt, Optional<ExecutionType> executionTypeOpt) {
		
		this.closurePeriod = new ClosurePeriod();
		this.closureIdHistories = new ArrayList<>();
		this.aggrPeriods = new ArrayList<>();

		// 集計すべき期間を計算
		this.calculatePeriodForAggregate(companyId, employeeId, criteriaDate);

		return this.closurePeriod;
	}
	
	/**
	 * 集計すべき期間を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrEndDate 集計終了日
	 */
	private void calculatePeriodForAggregate(String companyId, String employeeId, GeneralDate aggrEndDate){
	
		// 締められていない期間の締めIDを取得
		this.getClosureIdOfNotClosurePeriod(companyId, employeeId);
		
		for (val closureIdHistory: this.closureIdHistories){
			
			// 締めID履歴の期間内で締め処理期間を作成
			this.createAggrPeriodEachActualClosureWithinHistories(companyId, closureIdHistory, aggrEndDate);
		}
	}
	
	/**
	 * 締められていない期間の締めIDを取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 */
	private void getClosureIdOfNotClosurePeriod(String companyId, String employeeId){
		
		// 「所属雇用履歴」を取得する
		//*****（未） Requestが必要。（社員IDに該当する所属雇用履歴を開始日順にリスト取得する）
		//this.syEmploymentAdapter.findByEmployeeId(companyId, employeeId, baseDate);
		List<SyEmploymentImport> syEmployments = new ArrayList<>();
		
		for (val syEmployment : syEmployments){
			val employmentCd = syEmployment.getEmploymentCode();
			
			// 締めIDを取得する
			val closureEmploymentOpt = this.closureEmploymentRepository.findByEmploymentCD(employeeId, employmentCd);
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
			val syEmploymentPeriod = syEmployment.getPeriod();
			if (syEmploymentPeriod.end().before(closurePeriod.start())) continue;
			
			// 処理中の「所属雇用履歴」より前が締められたかチェックし、「締めID履歴」を作成
			if (syEmploymentPeriod.start().after(closurePeriod.start())){
				this.closureIdHistories.add(ClosureIdHistory.of(closureId, syEmploymentPeriod));
			}
			else {
				this.closureIdHistories.add(ClosureIdHistory.of(closureId,
						new DatePeriod(closurePeriod.start(), syEmploymentPeriod.end())));
			}
		}
	}
	
	/**
	 * 締めID履歴の期間内で締め処理期間を作成
	 * @param companyId 会社ID
	 * @param closureIdHistory 締めID履歴
	 * @param aggrEndDate 集計終了日
	 */
	private void createAggrPeriodEachActualClosureWithinHistories(
			String companyId, ClosureIdHistory closureIdHistory, GeneralDate aggrEndDate){
	
		// 指定した年月日時点の締め期間を取得する
		val closurePeriodOpt = this.closureService.getClosurePeriodByYmd(
				closureIdHistory.getClosureId().value, closureIdHistory.getPeriod().start());
		if (!closurePeriodOpt.isPresent()) return;
	}
}
