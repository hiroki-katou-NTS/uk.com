package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecordService;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;

/**
 * ドメインサービス：月別集計　（社員の月別実績を集計する）
 * @author shuichu_ishida
 */
@Stateless
@Transactional(value = TxType.REQUIRES_NEW)
public class MonthlyAggregationEmployeeServiceImpl implements MonthlyAggregationEmployeeService {

	/** ドメインサービス：月別実績を集計する */
	@Inject
	private AggregateMonthlyRecordService aggregateMonthlyRecordService;
	// （2018.3.1 shuichi_ishida）　単純入出力テスト用クラス
	//private MonthlyRelatedDataInOutTest aggregateMonthlyRecordService;
	/** 集計期間を取得する */
	@Inject
	private GetClosurePeriod getClosurePeriod;
	
	/** リポジトリ：月別実績の勤怠時間 */
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeRepository;
	/** リポジトリ：月別実績の所属情報 */
	@Inject
	private AffiliationInfoOfMonthlyRepository affiliationInfoRepository;
	/** リポジトリ：月別実績の任意項目 */
	@Inject
	private AnyItemOfMonthlyRepository anyItemRepository;
	/** リポジトリ：管理時間の36協定時間 */
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeRepository;
	/** 年休月別残数データ */
	@Inject
	private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;
	/** 積立年休月別残数データ */
	@Inject
	private RsvLeaRemNumEachMonthRepository rsvLeaRemNumEachMonthRepo;
	/** エラーメッセージ情報 */
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;
	
	/**
	 * 社員の月別実績を集計する
	 * @param asyncContext 同期コマンドコンテキスト
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
	 * @param executionType 実行種別　（通常、再実行）
	 */
	@Override
	public ProcessState aggregate(AsyncCommandHandlerContext asyncContext, String companyId, String employeeId,
			GeneralDate criteriaDate, String empCalAndSumExecLogID, ExecutionType executionType) {
		
		ProcessState status = ProcessState.SUCCESS;
		val dataSetter = asyncContext.getDataSetter();
		
		// 前回集計結果　（年休積立年休の集計結果）
		AggrResultOfAnnAndRsvLeave prevAggrResult = new AggrResultOfAnnAndRsvLeave();
		
		// 集計期間の判断　（実締め毎集計期間だけをすべて取り出す）
		List<AggrPeriodEachActualClosure> aggrPeriods = new ArrayList<>();
		val closurePeriods = this.getClosurePeriod.get(companyId, employeeId, criteriaDate,
				Optional.empty(), Optional.empty(), Optional.empty());
		for (val closurePeriod : closurePeriods) aggrPeriods.addAll(closurePeriod.getAggrPeriods());
		
		for (val aggrPeriod : aggrPeriods){
			val yearMonth = aggrPeriod.getYearMonth();
			val closureId = aggrPeriod.getClosureId();
			val closureDate = aggrPeriod.getClosureDate();
			val datePeriod = aggrPeriod.getPeriod();
			
			// 中断依頼が出されているかチェックする
			if (asyncContext.hasBeenRequestedToCancel()) {
				asyncContext.finishedAsCancelled();
				return ProcessState.INTERRUPTION;
			}
			
			// アルゴリズム「実績ロックされているか判定する」を実行する
			// ＞ロックされていれば、親に「中断」を返す
			//*****（未）　共通処理として作る必要がある。現時点では、日別作成の中にprivateで作られているため、共有できない。
			
			// 月別実績を集計する　（アルゴリズム）
			val value = this.aggregateMonthlyRecordService.aggregate(companyId, employeeId,
					yearMonth, closureId, closureDate, datePeriod, prevAggrResult);
			if (value.getErrorInfos().size() > 0) {

				// 「エラーあり」に更新
				dataSetter.updateData("monthlyAggregateHasError", ErrorPresent.HAS_ERROR.nameId);
				
				// エラー出力
				List<MonthlyAggregationErrorInfo> errorInfoList = new ArrayList<>();
				errorInfoList.addAll(value.getErrorInfos().values());
				errorInfoList.sort((a, b) -> a.getResourceId().compareTo(b.getResourceId()));
				for (val errorInfo : errorInfoList){
					this.errMessageInfoRepository.add(new ErrMessageInfo(
							employeeId,
							empCalAndSumExecLogID,
							new ErrMessageResource(errorInfo.getResourceId()),
							ExecutionContent.MONTHLY_AGGREGATION,
							datePeriod.end(),
							errorInfo.getMessage()));
				}
				
				// 中断するエラーがある時、中断処理をする
				if (value.isInterruption()){
					asyncContext.finishedAsCancelled();
					return ProcessState.INTERRUPTION;
				}
			}
			
			// 前回集計結果の退避
			prevAggrResult = value.getAggrResultOfAnnAndRsvLeave();
			
			// 計算結果と同月データの削除
			val oldDatas = this.attendanceTimeRepository.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
			for (val oldData : oldDatas){
				boolean isTarget = false;
				if (oldData.getClosureId().value != closureId.value) isTarget = true;
				if (!oldData.getClosureDate().getClosureDay().equals(closureDate.getClosureDay())) isTarget = true;
				if (oldData.getClosureDate().getLastDayOfMonth() != closureDate.getLastDayOfMonth()) isTarget = true;
				if (!isTarget) continue;
				this.attendanceTimeRepository.remove(
						employeeId, yearMonth, oldData.getClosureId(), oldData.getClosureDate());
			}
			
			// 登録する
			for (val attendanceTime : value.getAttendanceTimeList()){
				this.attendanceTimeRepository.persistAndUpdate(attendanceTime);
			}
			for (val affiliationInfo : value.getAffiliationInfoList()){
				this.affiliationInfoRepository.persistAndUpdate(affiliationInfo);
			}
			for (val anyItem : value.getAnyItemList()){
				this.anyItemRepository.persistAndUpdate(anyItem);
			}
			for (val agreementTime : value.getAgreementTimeList()){
				this.agreementTimeRepository.persistAndUpdate(agreementTime);
			}
			for (val annLeaRemNum : value.getAnnLeaRemNumEachMonthList()){
				this.annLeaRemNumEachMonthRepo.persistAndUpdate(annLeaRemNum);
			}
			for (val rsvLeaRemNum : value.getRsvLeaRemNumEachMonthList()){
				this.rsvLeaRemNumEachMonthRepo.persistAndUpdate(rsvLeaRemNum);
			}
		}
		return status;
	}
}
