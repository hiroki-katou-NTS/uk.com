package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 処理：暫定年休管理データを作成する
 * @author shuichu_ishida
 */
public class CreateTempAnnLeaMngProc {
	
	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 期間 */
	private DatePeriod period;
	/** モード */
	private InterimRemainMngMode mode;
	
	/** 月別集計で必要な会社別設定 */
	private MonAggrCompanySettings companySets;
	/** 月の計算中の日別実績データ */
	private MonthlyCalculatingDailys monthlyCalculatingDailys;
	
	/** 日別実績の勤務情報リスト */
	private Map<GeneralDate, WorkInfoOfDailyAttendance> workInfoOfDailys;
	/** 勤務予定基本情報リスト **/
	private Map<GeneralDate, BasicScheduleSidDto> basicSchedules;
	/** 暫定年休管理データリスト */
	private List<InterimRemain> tempAnnualLeaveMngs;
	
	private List<TmpAnnualHolidayMng> holidayMngs;
	/** 勤務種類リスト */
	private Map<String, WorkType> workTypeMap;
	/** 休暇加算設定 */
	private VacationAddSet vacationAddSet;
	
	public CreateTempAnnLeaMngProc() {
		
		this.workInfoOfDailys = new HashMap<>();
		this.basicSchedules = new HashMap<>();
		this.tempAnnualLeaveMngs = new ArrayList<>();
		this.workTypeMap = new HashMap<>();
	}
	
	/**
	 * 勤務種類の取得
	 * @param workTypeCode 勤務種類コード
	 * @return 勤務種類
	 */
	private WorkType getWorkType(RequireM4 require, String workTypeCode){
		
		if (this.workTypeMap.containsKey(workTypeCode)) return this.workTypeMap.get(workTypeCode);
		
		val workTypeOpt = require.workType(this.companyId, workTypeCode);
		if (workTypeOpt.isPresent()){
			this.workTypeMap.put(workTypeCode, workTypeOpt.get());
		}
		else {
			this.workTypeMap.put(workTypeCode, null);
		}
		return this.workTypeMap.get(workTypeCode);
	}

	/**
	 * 暫定年休管理データを作成する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param mode モード
	 * @return 暫定年休管理データリスト
	 */
	public AlgorithmResult algorithm(RequireM3 require,
			String companyId, String employeeId, DatePeriod period, InterimRemainMngMode mode) {

		return this.algorithm(require, companyId, employeeId, period, mode, 
				Optional.empty(), Optional.empty());
	}
	
	/**
	 * 暫定年休管理データを作成する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param mode モード
	 * @param companySets 月別集計で必要な会社別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @return 暫定年休管理データリスト
	 */
	public AlgorithmResult algorithm(RequireM3 require,
			String companyId, String employeeId, DatePeriod period,
			InterimRemainMngMode mode, Optional<MonAggrCompanySettings> companySets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.period = period;
		this.mode = mode;
		
		this.companySets = null;
		this.monthlyCalculatingDailys = null;
		if (companySets.isPresent()) this.companySets = companySets.get();
		if (monthlyCalcDailys.isPresent()) this.monthlyCalculatingDailys = monthlyCalcDailys.get();
		
		if (this.companySets != null){
			
			// 休暇加算設定　取得
			this.vacationAddSet = this.companySets.getVacationAddSet();

			// 勤務種類　取得
			this.workTypeMap = this.companySets.getAllWorkTypeMap();
		}
		else {
			
			// 休暇加算設定　取得
			this.vacationAddSet = GetVacationAddSet.get(require, companyId);

			// 勤務種類　取得
			this.workTypeMap = new HashMap<>();
		}
		
		// 暫定データ作成用の勤務予定・勤務実績・申請を取得
		this.getSourceDataForCreate(require);
		
		// 暫定管理データを作成する
		this.createTempManagementData(require);
		
		List<AtomTask> atomTask = new ArrayList<>();
		//　「モード」をチェック
		if (mode == InterimRemainMngMode.OTHER){

			// 「暫定年休管理データ」をDELETEする
			atomTask.add(AtomTask.of(() -> require.removeInterimRemain(employeeId, period)));
			
			// 「暫定年休管理データ」をINSERTする
			for (val tempAnnualLeaveMng : this.tempAnnualLeaveMngs) {
				atomTask.add(AtomTask.of(() -> require.persistAndUpdateInterimRemain(tempAnnualLeaveMng)));
			}
			
			// 「個人残数更新フラグ管理．更新状況」を「更新済み」にUpdateする
			//*****（未）　設計待ち。
		}
		
		// 年休フレックス補填分を暫定年休管理データに反映する
		this.reflecttempManagementDataFromCompFlex(require);
		
		// 作成した暫定年休管理データを返す
		return new AlgorithmResult(this.tempAnnualLeaveMngs, AtomTask.bundle(atomTask));
	}
	
	/**
	 * 暫定データ作成用の勤務予定・勤務実績・申請を取得
	 */
	private void getSourceDataForCreate(RequireM2 require){
	
		// 「日別実績の勤務情報」を取得する　→　取得した「日別実績の勤務情報」を返す
		if (this.monthlyCalculatingDailys != null) {
			this.workInfoOfDailys = this.monthlyCalculatingDailys.getWorkInfoOfDailyMap();
		}
		else {
			val acquiredWorkInfos = require.dailyWorkInfos(this.employeeId, this.period);
			for (val acquiredWorkInfo : acquiredWorkInfos.entrySet()){
				val ymd = acquiredWorkInfo.getKey();
				this.workInfoOfDailys.putIfAbsent(ymd, acquiredWorkInfo.getValue());
			}
		}
		
		// 「モード」をチェックする
		if (this.mode == InterimRemainMngMode.OTHER){

			// 社員．期間に未反映の申請を取得する　→　取得した「申請」を返す
			//*****（未）　RequestList依頼待ち。requestコンテキストの処理を呼んで、データを貰う。
		
			// 「勤務予定基本情報」を取得する　→　取得した「勤務予定基本情報」を返す
			//*****（未）　RequestList#141の完成待ち。仮に、今ある他の機能で代替。
			GeneralDate procDate = this.period.start();
			while (procDate.beforeOrEquals(this.period.end())){
				val basicScheduleSidOpt = require.basicScheduleSid(
						this.employeeId, procDate);
				if (basicScheduleSidOpt.isPresent()){
					val basicScheduleSid = basicScheduleSidOpt.get();
					val ymd = basicScheduleSid.getDate();
					this.basicSchedules.putIfAbsent(ymd, basicScheduleSid);
				}
				procDate = procDate.addDays(1);
			}
		}
	}
	
	/**
	 * 暫定管理データを作成する
	 */
	private void createTempManagementData(RequireM4 require){
		
		// 期間開始日～終了日まで1日づつループする
		for (GeneralDate procDate = this.period.start();
				procDate.beforeOrEquals(this.period.end()); procDate = procDate.addDays(1)){

			// 「日別実績の勤務情報」を取得する
			if (this.workInfoOfDailys.containsKey(procDate)){
				
				// 日別実績から暫定年休管理データを作成する
				this.createTempManagementDataFromDailyRecord(require, procDate, this.workInfoOfDailys.get(procDate));
				continue;
			}
			
			// 「勤務予定基本情報」を取得する
			if (this.basicSchedules.containsKey(procDate)){
				
				// 勤務予定から暫定年休管理データを作成する
				this.createTempManagementDataFromWorkSchedule(require, this.basicSchedules.get(procDate));
				continue;
			}
		}
	}
	
	/**
	 * 日別実績から暫定年休管理データを作成する
	 * @param workInfo 日別実績の勤務情報
	 */
	private void createTempManagementDataFromDailyRecord(RequireM4 require, GeneralDate ymd,
			WorkInfoOfDailyAttendance workInfo){
	
		// 勤務種類から年休の日数を取得
		val workTypeCode = workInfo.getRecordInfo().getWorkTypeCode();
		if (workTypeCode == null) return;
		val workType = this.getWorkType(require, workTypeCode.v());
		if (workType == null) return;
		val workTypeDaysCountTable = new WorkTypeDaysCountTable(workType, this.vacationAddSet, Optional.empty());
		
		String annualId = IdentifierUtil.randomUniqueId();
		InterimRemain tempAnnualLeaveMng2 = new InterimRemain(this.employeeId, ymd, annualId);
		TmpAnnualHolidayMng annualHolidayMng = new TmpAnnualHolidayMng(annualId);
		val annualLeaveDays = workTypeDaysCountTable.getAnnualLeaveDays();
		if (annualLeaveDays.greaterThan(0.0)){
			
			// 暫定年休管理データを作成
			annualHolidayMng.setUseDays(new UseDay(annualLeaveDays.v()));
			annualHolidayMng.setWorkTypeCode(workTypeCode.v());
			tempAnnualLeaveMng2.setCreatorAtr(CreateAtr.RECORD);

			// 「暫定年休管理データ」を返す
			this.tempAnnualLeaveMngs.add(tempAnnualLeaveMng2);
			this.holidayMngs.add(annualHolidayMng);
		}
	}
	
	/**
	 * 勤務予定から暫定年休管理データを作成する
	 * @param basicSchedule 勤務予定基本情報
	 */
	private void createTempManagementDataFromWorkSchedule(RequireM4 require, BasicScheduleSidDto basicSchedule){
		
		// 勤務種類から年休の日数を取得
		val workTypeCode = new WorkTypeCode(basicSchedule.getWorkTypeCode());
		val workType = this.getWorkType(require, workTypeCode.v());
		if (workType == null) return;
		val workTypeDaysCountTable = new WorkTypeDaysCountTable(workType, this.vacationAddSet, Optional.empty());
		
		val ymd = basicSchedule.getDate();
		String annualId = IdentifierUtil.randomUniqueId();
		InterimRemain tempAnnualLeaveMng2 = new InterimRemain(this.employeeId, ymd, annualId);
		TmpAnnualHolidayMng annualHolidayMng = new TmpAnnualHolidayMng(annualId);
		
		val annualLeaveDays = workTypeDaysCountTable.getAnnualLeaveDays();
		if (annualLeaveDays.greaterThan(0.0)){
			
			// 暫定年休管理データを作成
			annualHolidayMng.setUseDays(new UseDay(annualLeaveDays.v()));
			annualHolidayMng.setWorkTypeCode(workTypeCode.v());
			tempAnnualLeaveMng2.setCreatorAtr(CreateAtr.RECORD);

			// 「暫定年休管理データ」を返す
			this.tempAnnualLeaveMngs.add(tempAnnualLeaveMng2);
			this.holidayMngs.add(annualHolidayMng);
		}
	}
	
	/**
	 * 年休フレックス補填分を暫定年休管理データに反映する
	 */
	private void reflecttempManagementDataFromCompFlex(RequireM1 require){

		// 「月別実績の勤怠時間」を取得
		val attendanceTimeOfMonthlys =
				require.attendanceTimeOfMonthly(this.employeeId, this.period.end());
		for (val attendanceTimeOfMonthly : attendanceTimeOfMonthlys){
			val monthlyCalc = attendanceTimeOfMonthly.getMonthlyCalculation();
			
			// 「暫定年休管理データ」を作成
			
			InterimRemain tempAnnualLeaveMng2 = new InterimRemain(this.employeeId, attendanceTimeOfMonthly.getDatePeriod().end());
			TmpAnnualHolidayMng annualHolidayMng = new TmpAnnualHolidayMng();
			
			annualHolidayMng.setUseDays(new UseDay(
					monthlyCalc.getFlexTime().getFlexShortDeductTime().getAnnualLeaveDeductDays().v()));
			annualHolidayMng.setWorkTypeCode(new WorkTypeCode("DMY").v());
			tempAnnualLeaveMng2.setCreatorAtr(CreateAtr.RECORD);
			
			// 「暫定年休管理データ」に追加
			this.tempAnnualLeaveMngs.add(tempAnnualLeaveMng2);
			this.holidayMngs.add(annualHolidayMng);
		}
	}
	
	@AllArgsConstructor
	@Getter
	public class AlgorithmResult {
		
		private List<InterimRemain> tempAnnualLeaveMngs;
		
		private AtomTask atomTask;
	}
	
	public static interface RequireM3 extends RequireM4, RequireM2, RequireM1,
		GetVacationAddSet.RequireM1 {
		
		void persistAndUpdateInterimRemain(InterimRemain domain);
		
		void removeInterimRemain(String sId, DatePeriod period);
	}
	
	public static interface RequireM4 {
		
		Optional<WorkType> workType(String companyId, String workTypeCd);
	}
	
	public static interface RequireM2 {
		
		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, GeneralDate criteriaDate);
		
		Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfos(String employeeId, DatePeriod datePeriod);
		
		Optional<BasicScheduleSidDto> basicScheduleSid(String employeeId, GeneralDate baseDate);
	}
	
	public static interface RequireM1 {
		
		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, GeneralDate criteriaDate);
	}
}
