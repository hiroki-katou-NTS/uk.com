package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 処理：暫定年休管理データを作成する
 * @author shuichu_ishida
 */
public class CreateTempAnnLeaMngProc {

	/** 日別実績の勤務情報 */
	private WorkInformationRepository workInformationRepo;
	/** 勤務予定基本情報 */
	private BasicScheduleAdapter basicScheduleAdapter;
	/** 暫定年休管理データ */
	private TempAnnualLeaveMngRepository tempAnnualLeaveMngRepo;
	/** 勤務情報の取得 */
	private WorkTypeRepository workTypeRepo;
	/** 休暇加算設定の取得 */
	private GetVacationAddSet getVacationAddSet;
	/** 月別実績の勤怠時間 */
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	
	/** 社員ID */
	private String employeeId;
	/** 期間 */
	private DatePeriod period;
	/** モード */
	private TempAnnualLeaveMngMode mode;
	
	/** 日別実績の勤務情報リスト */
	private Map<GeneralDate, WorkInfoOfDailyPerformance> workInfoOfDailys;
	/** 勤務予定基本情報リスト **/
	private Map<GeneralDate, BasicScheduleSidDto> basicSchedules;
	/** 暫定年休管理データリスト */
	private List<TempAnnualLeaveManagement> tempAnnualLeaveMngs;
	/** 勤務種類リスト */
	private Map<WorkTypeCode, WorkType> workTypeMap;
	/** 休暇加算設定 */
	private VacationAddSet vacationAddSet;
	
	public CreateTempAnnLeaMngProc(
			WorkInformationRepository workInformationRepo,
			BasicScheduleAdapter basicScheduleAdapter,
			TempAnnualLeaveMngRepository tempAnnualLeaveMngRepo,
			WorkTypeRepository workTypeRepo,
			GetVacationAddSet getVacationAddSet,
			AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo) {
		
		this.workInformationRepo = workInformationRepo;
		this.basicScheduleAdapter = basicScheduleAdapter;
		this.tempAnnualLeaveMngRepo = tempAnnualLeaveMngRepo;
		this.workTypeRepo = workTypeRepo;
		this.getVacationAddSet = getVacationAddSet;
		this.attendanceTimeOfMonthlyRepo = attendanceTimeOfMonthlyRepo;
	}
	
	/**
	 * 暫定年休管理データを作成する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param mode モード
	 * @return 暫定年休管理データリスト
	 */
	public List<TempAnnualLeaveManagement> algorithm(String companyId, String employeeId, DatePeriod period,
			TempAnnualLeaveMngMode mode) {
		
		this.employeeId = employeeId;
		this.period = period;
		this.mode = mode;
		
		this.workInfoOfDailys = new HashMap<>();
		this.basicSchedules = new HashMap<>();
		this.tempAnnualLeaveMngs = new ArrayList<>();

		// 休暇加算設定　取得
		this.vacationAddSet = this.getVacationAddSet.get(companyId);
		
		// 勤務情報　取得
		val workTypes = this.workTypeRepo.findByCompanyId(companyId);
		this.workTypeMap = new HashMap<>();
		for (val workType : workTypes){
			val workTypeCode = workType.getWorkTypeCode();
			this.workTypeMap.putIfAbsent(workTypeCode, workType);
		}
		
		// 暫定データ作成用の勤務予定・勤務実績・申請を取得
		this.getSourceDataForCreate();
		
		// 暫定管理データを作成する
		this.createTempManagementData();
		
		//　「モード」をチェック
		if (mode == TempAnnualLeaveMngMode.OTHER){

			// 「暫定年休管理データ」をDELETEする
			this.tempAnnualLeaveMngRepo.removeByPeriod(employeeId, period);
			
			// 「暫定年休管理データ」をINSERTする
			for (val tempAnnualLeaveMng : this.tempAnnualLeaveMngs){
				this.tempAnnualLeaveMngRepo.persistAndUpdate(tempAnnualLeaveMng);
			}
			
			// 「個人残数更新フラグ管理．更新状況」を「更新済み」にUpdateする
			//*****（未）　設計待ち。
		}
		
		// 年休フレックス補填分を暫定年休管理データに反映する
		this.reflecttempManagementDataFromCompFlex();
		
		// 作成した暫定年休管理データを返す
		return this.tempAnnualLeaveMngs;
	}
	
	/**
	 * 暫定データ作成用の勤務予定・勤務実績・申請を取得
	 */
	private void getSourceDataForCreate(){
	
		List<WorkInfoOfDailyPerformance> acquiredWorkInfos = new ArrayList<>();
		List<BasicScheduleSidDto> acquiredBasicSchedules = new ArrayList<>();
		
		// 「モード」をチェックする
		if (this.mode == TempAnnualLeaveMngMode.MONTHLY){
			
			// 「日別実績の勤務情報」を取得する
			acquiredWorkInfos = this.workInformationRepo.findByPeriodOrderByYmd(this.employeeId, this.period);
		}
		if (this.mode == TempAnnualLeaveMngMode.OTHER){

			// 社員．期間に未反映の申請を取得する
			//*****（未）　RequestList依頼待ち。requestコンテキストの処理を呼んで、データを貰う。
			
			// 「日別実績の勤務情報」を取得する
			acquiredWorkInfos = this.workInformationRepo.findByPeriodOrderByYmd(this.employeeId, this.period);
		
			// 「勤務予定基本情報」を取得する
			//*****（未）　RequestList#141の完成待ち。仮に、今ある他の機能で代替。
			GeneralDate procDate = this.period.start();
			while (procDate.beforeOrEquals(this.period.end())){
				val basicScheduleSidOpt = this.basicScheduleAdapter.findAllBasicSchedule(
						this.employeeId, procDate);
				if (basicScheduleSidOpt.isPresent()) acquiredBasicSchedules.add(basicScheduleSidOpt.get());
				procDate = procDate.addDays(1);
			}
		}

		// 取得した「勤務予定基本情報」「日別実績の勤務情報」「申請」を返す
		for (val acquiredWorkInfo : acquiredWorkInfos){
			val ymd = acquiredWorkInfo.getYmd();
			this.workInfoOfDailys.putIfAbsent(ymd, acquiredWorkInfo);
		}
		for (val acquiredBasicSchedule : acquiredBasicSchedules){
			val ymd = acquiredBasicSchedule.getDate();
			this.basicSchedules.putIfAbsent(ymd, acquiredBasicSchedule);
		}
	}
	
	/**
	 * 暫定管理データを作成する
	 */
	private void createTempManagementData(){
		
		// 期間開始日～終了日まで1日づつループする
		for (GeneralDate procDate = this.period.start();
				procDate.beforeOrEquals(this.period.end()); procDate = procDate.addDays(1)){

			// 「日別実績の勤務情報」を取得する
			if (this.workInfoOfDailys.containsKey(procDate)){
				
				// 日別実績から暫定年休管理データを作成する
				this.createTempManagementDataFromDailyRecord(this.workInfoOfDailys.get(procDate));
				continue;
			}
			
			// 「勤務予定基本情報」を取得する
			if (this.basicSchedules.containsKey(procDate)){
				
				// 勤務予定から暫定年休管理データを作成する
				this.createTempManagementDataFromWorkSchedule(this.basicSchedules.get(procDate));
				continue;
			}
		}
	}
	
	/**
	 * 日別実績から暫定年休管理データを作成する
	 * @param workInfo 日別実績の勤務情報
	 */
	private void createTempManagementDataFromDailyRecord(WorkInfoOfDailyPerformance workInfo){
	
		// 勤務種類から年休の日数を取得
		val workTypeCode = workInfo.getRecordInfo().getWorkTypeCode();
		if (!this.workTypeMap.containsKey(workTypeCode)) return;
		val workType = this.workTypeMap.get(workTypeCode);
		val workTypeDaysCountTable = new WorkTypeDaysCountTable(workType, this.vacationAddSet, Optional.empty());
		
		val ymd = workInfo.getYmd();
		TempAnnualLeaveManagement tempAnnualLeaveMng = new TempAnnualLeaveManagement(this.employeeId, ymd);
		
		val annualLeaveDays = workTypeDaysCountTable.getAnnualLeaveDays();
		if (annualLeaveDays.greaterThan(0.0)){
			
			// 暫定年休管理データを作成
			tempAnnualLeaveMng.setAnnualLeaveUse(new ManagementDays(annualLeaveDays.v()));
			tempAnnualLeaveMng.setWorkType(workTypeCode);
			tempAnnualLeaveMng.setScheduleRecordAtr(ScheduleRecordAtr.RECORD);

			// 「暫定年休管理データ」を返す
			this.tempAnnualLeaveMngs.add(tempAnnualLeaveMng);
		}
	}
	
	/**
	 * 勤務予定から暫定年休管理データを作成する
	 * @param basicSchedule 勤務予定基本情報
	 */
	private void createTempManagementDataFromWorkSchedule(BasicScheduleSidDto basicSchedule){
		
		// 勤務種類から年休の日数を取得
		val workTypeCode = new WorkTypeCode(basicSchedule.getWorkTypeCode());
		if (!this.workTypeMap.containsKey(workTypeCode)) return;
		val workType = this.workTypeMap.get(workTypeCode);
		val workTypeDaysCountTable = new WorkTypeDaysCountTable(workType, this.vacationAddSet, Optional.empty());
		
		val ymd = basicSchedule.getDate();
		TempAnnualLeaveManagement tempAnnualLeaveMng = new TempAnnualLeaveManagement(this.employeeId, ymd);
		
		val annualLeaveDays = workTypeDaysCountTable.getAnnualLeaveDays();
		if (annualLeaveDays.greaterThan(0.0)){
			
			// 暫定年休管理データを作成
			tempAnnualLeaveMng.setAnnualLeaveUse(new ManagementDays(annualLeaveDays.v()));
			tempAnnualLeaveMng.setWorkType(workTypeCode);
			tempAnnualLeaveMng.setScheduleRecordAtr(ScheduleRecordAtr.SCHEDULE);

			// 「暫定年休管理データ」を返す
			this.tempAnnualLeaveMngs.add(tempAnnualLeaveMng);
		}
	}
	
	/**
	 * 年休フレックス補填分を暫定年休管理データに反映する
	 */
	private void reflecttempManagementDataFromCompFlex(){

		// 「月別実績の勤怠時間」を取得
		val attendanceTimeOfMonthlys =
				this.attendanceTimeOfMonthlyRepo.findByDate(this.employeeId, this.period.end());
		for (val attendanceTimeOfMonthly : attendanceTimeOfMonthlys){
			val monthlyCalc = attendanceTimeOfMonthly.getMonthlyCalculation();
			
			// 「暫定年休管理データ」を作成
			TempAnnualLeaveManagement tempAnnualLeaveMng = new TempAnnualLeaveManagement(
					this.employeeId, attendanceTimeOfMonthly.getDatePeriod().end());
			tempAnnualLeaveMng.setAnnualLeaveUse(new ManagementDays(
					monthlyCalc.getFlexTime().getFlexShortDeductTime().getAnnualLeaveDeductDays().v()));
			tempAnnualLeaveMng.setWorkType(new WorkTypeCode("DMY"));
			tempAnnualLeaveMng.setScheduleRecordAtr(ScheduleRecordAtr.RECORD);
			
			// 「暫定年休管理データ」に追加
			this.tempAnnualLeaveMngs.add(tempAnnualLeaveMng);
		}
	}
}
