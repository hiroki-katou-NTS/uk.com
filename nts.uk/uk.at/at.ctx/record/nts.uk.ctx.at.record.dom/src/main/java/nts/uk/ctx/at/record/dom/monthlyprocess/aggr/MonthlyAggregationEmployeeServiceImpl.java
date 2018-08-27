package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.diagnose.stopwatch.concurrent.ConcurrentStopwatches;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainDataRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainDataRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainDataRepository;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.procedure.ProcMonthlyData;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecordService;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：月別集計　（社員の月別実績を集計する）
 * @author shuichi_ishida
 */
@Stateless
public class MonthlyAggregationEmployeeServiceImpl implements MonthlyAggregationEmployeeService {

	/** ドメインサービス：月別実績を集計する */
	@Inject
	private AggregateMonthlyRecordService aggregateMonthlyRecordService;
	// （2018.3.1 shuichi_ishida）　単純入出力テスト用クラス
	//private MonthlyRelatedDataInOutTest aggregateMonthlyRecordService;
	/** 集計期間を取得する */
	@Inject
	private GetClosurePeriod getClosurePeriod;
	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	
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
	/** 振休月別残数データ */
	@Inject
	private AbsenceLeaveRemainDataRepository absLeaRemRepo;
	/** 代休月別残数データ */
	@Inject
	private MonthlyDayoffRemainDataRepository monDayoffRemRepo;
	/** 特別休暇月別残数データ */
	@Inject
	private SpecialHolidayRemainDataRepository spcLeaRemRepo;
	/** エラーメッセージ情報 */
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;
	/** 月別実績データストアドプロシージャ */
	@Inject
	private ProcMonthlyData procMonthlyData;
	
	/** 社員の月別実績を集計する */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public ProcessState aggregate(AsyncCommandHandlerContext asyncContext, String companyId, String employeeId,
			GeneralDate criteriaDate, String empCalAndSumExecLogID, ExecutionType executionType) {

		ProcessState status = ProcessState.SUCCESS;
		val dataSetter = asyncContext.getDataSetter();
		
		// 月別集計で必要な会社別設定を取得する
		val companySets = MonAggrCompanySettings.loadSettings(companyId, this.repositories);
		if (companySets.getErrorInfos().size() > 0){
			
			// エラー処理
			List<MonthlyAggregationErrorInfo> errorInfoList = new ArrayList<>();
			for (val errorInfo : companySets.getErrorInfos().entrySet()){
				errorInfoList.add(new MonthlyAggregationErrorInfo(errorInfo.getKey(), errorInfo.getValue()));
			}
			this.errorProc(dataSetter, employeeId, empCalAndSumExecLogID, criteriaDate, errorInfoList);
			return status;
		}
		
		val aggrStatus = this.aggregate(asyncContext, companyId, employeeId, criteriaDate,
				empCalAndSumExecLogID, executionType, companySets);
		
		// 出力したデータに関連するキー値でストアドプロシージャを実行する
		for (val aggrPeriod : aggrStatus.getOutAggrPeriod()){
			this.procMonthlyData.execute(
					companyId,
					employeeId,
					aggrPeriod.getYearMonth(),
					aggrPeriod.getClosureId(),
					aggrPeriod.getClosureDate());
		}
		
		return aggrStatus.getState();
	}
	
	/** 社員の月別実績を集計する */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public MonthlyAggrEmpServiceValue aggregate(AsyncCommandHandlerContext asyncContext, String companyId, String employeeId,
			GeneralDate criteriaDate, String empCalAndSumExecLogID, ExecutionType executionType,
			MonAggrCompanySettings companySets) {
		
		MonthlyAggrEmpServiceValue status = new MonthlyAggrEmpServiceValue();
		val dataSetter = asyncContext.getDataSetter();
		
		// 前回集計結果　（年休積立年休の集計結果）
		AggrResultOfAnnAndRsvLeave prevAggrResult = new AggrResultOfAnnAndRsvLeave();

		ConcurrentStopwatches.start("11000:集計期間の判断：");
		
		// 集計期間の判断　（実締め毎集計期間だけをすべて取り出す）
		List<AggrPeriodEachActualClosure> aggrPeriods = new ArrayList<>();
		val closurePeriods = this.getClosurePeriod.get(companyId, employeeId, criteriaDate,
				Optional.empty(), Optional.empty(), Optional.empty());
		for (val closurePeriod : closurePeriods) aggrPeriods.addAll(closurePeriod.getAggrPeriods());
		
		// 全体の期間を求める
		DatePeriod allPeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		if (aggrPeriods.size() > 0){
			val headPeriod = aggrPeriods.get(0).getPeriod();
			allPeriod = new DatePeriod(headPeriod.start(), headPeriod.end());
			for (val aggrPeriod : aggrPeriods){
				GeneralDate startYmd = allPeriod.start();
				GeneralDate endYmd = allPeriod.end();
				if (startYmd.after(aggrPeriod.getPeriod().start())) startYmd = aggrPeriod.getPeriod().start();
				if (endYmd.before(aggrPeriod.getPeriod().end())) endYmd = aggrPeriod.getPeriod().end();
				allPeriod = new DatePeriod(startYmd, endYmd);
			}
		}
		
		// 月別集計で必要な社員別設定を取得
		val employeeSets = MonAggrEmployeeSettings.loadSettings(
				companyId, employeeId, allPeriod, this.repositories);
		if (employeeSets.getErrorInfos().size() > 0){
			
			// エラー処理
			List<MonthlyAggregationErrorInfo> errorInfoList = new ArrayList<>();
			for (val errorInfo : employeeSets.getErrorInfos().entrySet()){
				errorInfoList.add(new MonthlyAggregationErrorInfo(errorInfo.getKey(), errorInfo.getValue()));
			}
			this.errorProc(dataSetter, employeeId, empCalAndSumExecLogID, criteriaDate, errorInfoList);
			return status;
		}
		
		ConcurrentStopwatches.stop("11000:集計期間の判断：");
		
		for (val aggrPeriod : aggrPeriods){
			val yearMonth = aggrPeriod.getYearMonth();
			val closureId = aggrPeriod.getClosureId();
			val closureDate = aggrPeriod.getClosureDate();
			val datePeriod = aggrPeriod.getPeriod();
			
			//ConcurrentStopwatches.start("12000:集計期間ごと：" + aggrPeriod.getYearMonth().toString());
			
			// 中断依頼が出されているかチェックする
			if (asyncContext.hasBeenRequestedToCancel()) {
				asyncContext.finishedAsCancelled();
				status.setState(ProcessState.INTERRUPTION);
				return status;
			}
			
			// アルゴリズム「実績ロックされているか判定する」を実行する
			if (companySets.getDetermineActualLocked(datePeriod.end(), closureId.value) == LockStatus.LOCK){
				continue;
			}
			
			// 月別実績を集計する　（アルゴリズム）
			val value = this.aggregateMonthlyRecordService.aggregate(companyId, employeeId,
					yearMonth, closureId, closureDate, datePeriod, prevAggrResult, companySets, employeeSets,
					Optional.empty(), Optional.empty());
			if (value.getErrorInfos().size() > 0) {

				// エラー処理
				List<MonthlyAggregationErrorInfo> errorInfoList = new ArrayList<>();
				errorInfoList.addAll(value.getErrorInfos().values());
				this.errorProc(dataSetter, employeeId, empCalAndSumExecLogID, datePeriod.end(), errorInfoList);
				
				// 中断するエラーがある時、中断処理をする
				if (value.isInterruption()){
					asyncContext.finishedAsCancelled();
					status.setState(ProcessState.INTERRUPTION);
					return status;
				}
			}
			
			// 前回集計結果の退避
			prevAggrResult = value.getAggrResultOfAnnAndRsvLeave();
			
			// 計算結果と同月データ・締めID違いの削除
			val attendanceTimeOlds = this.attendanceTimeRepository.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
			for (val oldData : attendanceTimeOlds){
				boolean isTarget = false;
				if (oldData.getClosureId().value != closureId.value) isTarget = true;
				if (!isTarget) continue;
				this.attendanceTimeRepository.remove(
						employeeId, yearMonth, oldData.getClosureId(), oldData.getClosureDate());
			}
			val affiliationInfoOlds = this.affiliationInfoRepository.findBySidAndYearMonth(employeeId, yearMonth);
			for (val oldData : affiliationInfoOlds){
				boolean isTarget = false;
				if (oldData.getClosureId().value != closureId.value) isTarget = true;
				if (!isTarget) continue;
				this.affiliationInfoRepository.remove(
						employeeId, yearMonth, oldData.getClosureId(), oldData.getClosureDate());
			}
			val anyItemOlds = this.anyItemRepository.findByMonthly(employeeId, yearMonth);
			for (val oldData : anyItemOlds){
				boolean isTarget = false;
				if (oldData.getClosureId().value != closureId.value) isTarget = true;
				if (!isTarget) continue;
				this.anyItemRepository.remove(
						employeeId, yearMonth, oldData.getClosureId(), oldData.getClosureDate(), oldData.getAnyItemId());
			}
			val annLeaRemNumOlds = this.annLeaRemNumEachMonthRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
			for (val oldData : annLeaRemNumOlds){
				boolean isTarget = false;
				if (oldData.getClosureId().value != closureId.value) isTarget = true;
				if (!isTarget) continue;
				this.annLeaRemNumEachMonthRepo.remove(
						employeeId, yearMonth, oldData.getClosureId(), oldData.getClosureDate());
			}
			val rsvLeaRemNumOlds = this.rsvLeaRemNumEachMonthRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
			for (val oldData : rsvLeaRemNumOlds){
				boolean isTarget = false;
				if (oldData.getClosureId().value != closureId.value) isTarget = true;
				if (!isTarget) continue;
				this.rsvLeaRemNumEachMonthRepo.remove(
						employeeId, yearMonth, oldData.getClosureId(), oldData.getClosureDate());
			}
			val absLeaRemNumOlds = this.absLeaRemRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
			for (val oldData : absLeaRemNumOlds){
				boolean isTarget = false;
				if (oldData.getClosureId() != closureId.value) isTarget = true;
				if (!isTarget) continue;
				this.absLeaRemRepo.remove(employeeId, yearMonth,
						EnumAdaptor.valueOf(oldData.getClosureId(), ClosureId.class),
						new ClosureDate(oldData.getClosureDay(), oldData.isLastDayIs()));
			}
			val monDayoffRemNumOlds = this.monDayoffRemRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
			for (val oldData : monDayoffRemNumOlds){
				boolean isTarget = false;
				if (oldData.getClosureId() != closureId.value) isTarget = true;
				if (!isTarget) continue;
				this.monDayoffRemRepo.remove(employeeId, yearMonth,
						EnumAdaptor.valueOf(oldData.getClosureId(), ClosureId.class),
						new ClosureDate(oldData.getClosureDay(), oldData.isLastDayis()));
			}
			val spcLeaRemNumOlds = this.spcLeaRemRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
			for (val oldData : spcLeaRemNumOlds){
				boolean isTarget = false;
				if (oldData.getClosureId() != closureId.value) isTarget = true;
				if (!isTarget) continue;
				this.spcLeaRemRepo.remove(employeeId, yearMonth,
						EnumAdaptor.valueOf(oldData.getClosureId(), ClosureId.class), oldData.getClosureDate());
			}
			
			// 登録する
			if (value.getAttendanceTime().isPresent()){
				this.attendanceTimeRepository.persistAndUpdate(value.getAttendanceTime().get());
			}
			if (value.getAffiliationInfo().isPresent()){
				this.affiliationInfoRepository.persistAndUpdate(value.getAffiliationInfo().get());
			}
			for (val anyItem : value.getAnyItemList()){
				this.anyItemRepository.persistAndUpdate(anyItem);
			}
			if (value.getAgreementTime().isPresent()){
				this.agreementTimeRepository.persistAndUpdate(value.getAgreementTime().get());
			}
			for (val annLeaRemNum : value.getAnnLeaRemNumEachMonthList()){
				this.annLeaRemNumEachMonthRepo.persistAndUpdate(annLeaRemNum);
			}
			for (val rsvLeaRemNum : value.getRsvLeaRemNumEachMonthList()){
				this.rsvLeaRemNumEachMonthRepo.persistAndUpdate(rsvLeaRemNum);
			}
			for (val absLeaRemNum : value.getAbsenceLeaveRemainList()){
				this.absLeaRemRepo.persistAndUpdate(absLeaRemNum);
			}
			for (val monDayoffRemNum : value.getMonthlyDayoffRemainList()){
				this.monDayoffRemRepo.persistAndUpdate(monDayoffRemNum);
			}
			for (val spcLeaRemNum : value.getSpecialLeaveRemainList()){
				this.spcLeaRemRepo.persistAndUpdate(spcLeaRemNum);
			}
			
			status.getOutAggrPeriod().add(aggrPeriod);
			
			//ConcurrentStopwatches.stop("12000:集計期間ごと：" + aggrPeriod.getYearMonth().toString());
		}
		return status;
	}
	
	/**
	 * エラー処理
	 * @param dataSetter データセッター
	 * @param employeeId 社員ID
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
	 * @param outYmd 出力年月日
	 * @param errorInfoList エラー情報リスト
	 */
	private void errorProc(
			TaskDataSetter dataSetter,
			String employeeId,
			String empCalAndSumExecLogID,
			GeneralDate outYmd,
			List<MonthlyAggregationErrorInfo> errorInfoList){
		
		// 「エラーあり」に更新
		dataSetter.updateData("monthlyAggregateHasError", ErrorPresent.HAS_ERROR.nameId);
		
		// エラー出力
		errorInfoList.sort((a, b) -> a.getResourceId().compareTo(b.getResourceId()));
		for (val errorInfo : errorInfoList){
			this.errMessageInfoRepository.add(new ErrMessageInfo(
					employeeId,
					empCalAndSumExecLogID,
					new ErrMessageResource(errorInfo.getResourceId()),
					ExecutionContent.MONTHLY_AGGREGATION,
					outYmd,
					errorInfo.getMessage()));
		}
	}
}
