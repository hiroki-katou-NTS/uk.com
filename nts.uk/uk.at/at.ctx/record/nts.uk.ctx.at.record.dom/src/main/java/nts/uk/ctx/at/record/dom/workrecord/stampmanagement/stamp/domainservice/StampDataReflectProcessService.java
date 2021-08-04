package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyResult;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
//import nts.uk.ctx.at.record.dom.stamp.management.ReservationArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.EndStatus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

/**
 * @author ThanhNX
 *
 *         打刻データ反映処理
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻データ反映処理
 */
public class StampDataReflectProcessService {

	// [1] 反映する
	public static StampDataReflectResult reflect(Require require, String cid, Optional<String> employeeId, StampRecord stampRecord,
			Optional<Stamp> stamp) {
		
		//	$打刻記録
		if (require.getStampRecord(stampRecord.getContractCode(), stampRecord.getStampNumber(),
				stampRecord.getStampDateTime()).isPresent()) {
			return new StampDataReflectResult(Optional.empty(), AtomTask.none());
		}
		
		if (employeeId.isPresent()) {
			// $反映対象日 = [prv-3] いつの日別実績に反映するか(require, 社員ID, 打刻)
			Optional<GeneralDate> reflectDate = reflectDailyResult(require, cid, employeeId, stamp);
			// $AtomTask = AtomTask:
			AtomTask atomTask = AtomTask.of(() -> {
				// require.打刻記録を追加する(打刻記録)
				require.insert(stampRecord);
				// prv-1] 弁当を自動予約する(打刻)
				automaticallyBook(stampRecord, stamp);
				// if not 打刻.isEmpty
				if (stamp.isPresent()) {
					require.insert(stamp.get());
				}

			});
			// return 打刻データ反映処理結果#打刻データ反映処理結果($反映対象日, $AtomTask)
			return new StampDataReflectResult(reflectDate, atomTask);
		} else {

			AtomTask atomTask = AtomTask.of(() -> {
				// require.打刻記録を追加する(打刻記録)
				require.insert(stampRecord);
				// if not 打刻.isEmpty
				if (stamp.isPresent()) {
					require.insert(stamp.get());
				}

			});
			return new StampDataReflectResult(Optional.empty(), atomTask);
		}
	}

	/**
	 * 	[S-2] 日別実績に打刻を更新する
	 * @param require
	 * @param employeeId
	 * @param date
	 * @param stamp
	 * @return
	 */
	public static Optional<IntegrationOfDaily> updateStampToDaily(Require2 require, String cid, String employeeId, GeneralDate date, Stamp stamp) {
		//	$日別実績 = require.日別実績を作成する(社員ID, 年月日, しない, empty, empty, empty)
 		IntegrationOfDaily integrationOfDaily = require.findDaily(employeeId, date).orElse(createNull(employeeId, date));
		OutputCreateDailyOneDay dailyOneDay = require.createDailyResult(
				cid, 
				employeeId,
				date,
				ExecutionTypeDaily.CREATE,
				EmbossingExecutionFlag.ALL,
				integrationOfDaily);
		
		if (!dailyOneDay.getListErrorMessageInfo().isEmpty()){
			return Optional.empty();
		}
		
		//	$打刻反映範囲 = require.打刻反映時間帯を取得する($日別実績.日別実績の勤務情報)
		OutputTimeReflectForWorkinfo forWorkinfo = require.get(cid, 
				employeeId,
				date,
				dailyOneDay.getIntegrationOfDaily().getWorkInformation());
		
		if(forWorkinfo.getEndStatus() != EndStatus.NORMAL) {
			return Optional.empty();
		}
		
		// 	$変更区分 = 日別勤怠の何が変更されたか#日別勤怠の何が変更されたか(true, true, true, true)	
		ChangeDailyAttendance changeDailyAtt = new  ChangeDailyAttendance(true,
				true, 
				true,
				true,
				ScheduleRecordClassifi.RECORD,
				true);

		//	$反映後の打刻 = require.打刻を反映する($日別実績, $打刻反映範囲, 打刻)
		 require.reflectStamp(cid, stamp,
				forWorkinfo.getStampReflectRangeOutput(),
				dailyOneDay.getIntegrationOfDaily(),
				changeDailyAtt);
		
		return Optional.of(dailyOneDay.getIntegrationOfDaily());
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
	private static Optional<GeneralDate> reflectDailyResult(Require require, String cid, Optional<String> employeeId,
			Optional<Stamp> stamp) {
		// if 社員ID.isEmpty
		// if 打刻.isEmpty
		if (!employeeId.isPresent() || !stamp.isPresent())
			return Optional.empty();
		return ReflectDataStampDailyService.getJudgment(require, cid, employeeId.get(), stamp.get());
	}

	private static IntegrationOfDaily createNull(String sid, GeneralDate dateData) {
		return new IntegrationOfDaily(
				sid,
				dateData,
				null, 
				null, 
				null,
				Optional.empty(), 
				new ArrayList<>(), 
				Optional.empty(), 
				new BreakTimeOfDailyAttd(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				new ArrayList<>(),
				Optional.empty(),
				new ArrayList<>(),
				Optional.empty());
	}
	
	public static interface Require extends ReflectDataStampDailyService.Require{

		// [R-1] 打刻記録を追加する JpaStampDakokuRepository
		public void insert(StampRecord stampRecord);

		// [R-2] 打刻を追加する JpaStampDakokuRepository
		public void insert(Stamp stamp);

		// [R-3] 日別を作成する CreateDailyResultDomainServiceNew
		public OutputCreateDailyResult createDataNewNotAsync(String employeeId,
				DatePeriod periodTime, ExecutionAttr executionAttr, String companyId,
				ExecutionTypeDaily executionType,Optional<EmpCalAndSumExeLog> empCalAndSumExeLog, Optional<Boolean> checkLock);
		
		//[R-4] 打刻記録を取得する
		public Optional<StampRecord> getStampRecord(ContractCode contractCode, StampNumber stampNumber,
				GeneralDateTime dateTime);
	}
	
	public static interface Require2 {

		// [R-6] 日別実績を作成する
		OutputCreateDailyOneDay createDailyResult(String cid, String employeeId, GeneralDate ymd,
				ExecutionTypeDaily executionType, EmbossingExecutionFlag flag, IntegrationOfDaily integrationOfDaily);

		// [R-7] 打刻反映時間帯を取得する
		OutputTimeReflectForWorkinfo get(String companyId, String employeeId, GeneralDate ymd,
				WorkInfoOfDailyAttendance workInformation);	

		// [R-8] 打刻を反映する
		List<ErrorMessageInfo> reflectStamp(String companyId, Stamp stamp, StampReflectRangeOutput stampReflectRangeOutput,
				IntegrationOfDaily integrationOfDaily, ChangeDailyAttendance changeDailyAtt);
		
		// DailyRecordShareFinder
		//[R-9] 日別実績を取得する
		public Optional<IntegrationOfDaily> findDaily(String employeeId, GeneralDate date);

		// TODO: các require đang k giống trong code, chờ bug
		// http://192.168.50.4:3000/issues/109911
	}
	
}
