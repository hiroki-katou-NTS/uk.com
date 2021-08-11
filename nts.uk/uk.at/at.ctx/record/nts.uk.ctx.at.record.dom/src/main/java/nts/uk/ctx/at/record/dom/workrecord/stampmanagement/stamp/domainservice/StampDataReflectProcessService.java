package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;

/**
 * @author ThanhNX
 *
 *         打刻データ反映処理
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻データ反映処理
 */
public class StampDataReflectProcessService {

	// [1] 反映する
	public static StampDataReflectResult reflect(Require require, Optional<String> employeeId, StampRecord stampRecord,
			Optional<Stamp> stamp) {
		//$反映対象日 = [prv-3] いつの日別実績に反映するか(require, 社員ID, 打刻)		
		Optional<GeneralDate> reflectDate = reflectDailyResult(require, employeeId, stamp);
		// $AtomTask = AtomTask:
		AtomTask atomTask = AtomTask.of(() -> {
			// require.打刻記録を追加する(打刻記録)
			require.insert(stampRecord);
			//prv-1] 弁当を自動予約する(打刻)
			automaticallyBook(stampRecord, stamp);
			//if not 打刻.isEmpty
			if (stamp.isPresent()) {
				require.insert(stamp.get());
			}

		});
		// return 打刻データ反映処理結果#打刻データ反映処理結果($反映対象日, $AtomTask)
		return new StampDataReflectResult(reflectDate, atomTask);
	}

	/**
	 * [prv-1] 弁当を自動予約する
	 * 
	 * @param stampRecord
	 * @param stamp
	 * @return
	 */
	private static Optional<AtomTask> automaticallyBook(StampRecord stampRecord, Optional<Stamp> stamp) {
		/*
		 * if(stampRecord.getRevervationAtr() == ReservationArt.NONE ) { //TODO chờ hàm
		 * gì đó được viết bởi đội khác, để tạm là option return Optional.empty(); }
		 * 
		 * if(!stamp.isPresent()) { return Optional.empty(); }
		 * 
		 * if(stamp.get().getType().checkBookAuto()) { //TODO chờ hàm gì đó được viết
		 * bởi đội khác, để tạm là option return Optional.empty(); }
		 */
		
		return Optional.empty();
	}

	/**
	 * prv-3] いつの日別実績に反映するか
	 * 
	 * @param require
	 * @param employeeId
	 * @param stamp
	 */
	private static Optional<GeneralDate> reflectDailyResult(Require require, Optional<String> employeeId,
			Optional<Stamp> stamp) {
		// if 社員ID.isEmpty
		// if 打刻.isEmpty
		if (!employeeId.isPresent() || !stamp.isPresent())
			return Optional.empty();
		return ReflectDataStampDailyService.getJudgment(require, employeeId.get(), stamp.get());
	}

	public static interface Require extends ReflectDataStampDailyService.Require {

		// [R-1] 打刻記録を追加する JpaStampDakokuRepository
		public void insert(StampRecord stampRecord);

		// [R-2] 打刻を追加する JpaStampDakokuRepository
		public void insert(Stamp stamp);

		// [R-3] 日別を作成する CreateDailyResultDomainServiceNew
		public OutputCreateDailyResult createDataNewNotAsync(String employeeId,
				DatePeriod periodTime, ExecutionAttr executionAttr, String companyId,
				ExecutionTypeDaily executionType,Optional<EmpCalAndSumExeLog> empCalAndSumExeLog, Optional<Boolean> checkLock);

	}
}
