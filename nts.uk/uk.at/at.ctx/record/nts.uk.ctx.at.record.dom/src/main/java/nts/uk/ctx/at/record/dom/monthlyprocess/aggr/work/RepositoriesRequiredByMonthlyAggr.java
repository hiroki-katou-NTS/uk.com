package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.GetAggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthlyRepository;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.GetOfStatutoryWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.GetWeekStart;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 月別集計が必要とするリポジトリ
 * @author shuichu_ishida
 */
public interface RepositoriesRequiredByMonthlyAggr {

	/** 所属職場履歴の取得 */
	AffWorkplaceAdapter getAffWorkplaceAdapter();

	/** 所属雇用履歴の取得 */
	SyEmploymentAdapter getSyEmployment(); 
	
	/** 日別実績の勤怠時間の取得 */
	AttendanceTimeRepository getAttendanceTimeOfDaily();
	
	/** 日別実績の勤務情報の取得 */
	WorkInformationRepository getWorkInformationOfDaily();

	/** 日別実績の出退勤の取得 */
	TimeLeavingOfDailyPerformanceRepository getTimeLeavingOfDaily();

	/** 日別実績の特定日区分の取得 */
	SpecificDateAttrOfDailyPerforRepo getSpecificDateAtrOfDaily();
	
	/** 勤務情報の取得 */
	WorkTypeRepository getWorkType();
	
	/** 所定時間設定の取得 */
	PredetemineTimeSettingRepository getPredetermineTimeSet();

	/** 社員の日別積実績エラー一覧 */
	EmployeeDailyPerErrorRepository getEmployeeDailyError();
	
	/** 月別実績の勤怠時間の取得 */
	AttendanceTimeOfMonthlyRepository getAttendanceTimeOfMonthly();
	
	/** 月別実績集計設定の取得 */
	GetAggrSettingMonthly getAggrSettingMonthly();

	/** 月次集計の法定内振替順設定の取得 */
	LegalTransferOrderSetOfAggrMonthlyRepository getLegalTransferOrderSetOfAggrMonthly();
	
	/** 法定労働時間の取得 */
	GetOfStatutoryWorkTime getGetOfStatutoryWorkTime();
	
	/** 休日加算設定 */
	HolidayAddtionRepository getHolidayAddition();
	
	/** 年休設定 */
	AnnualPaidLeaveSettingRepository getAnnualPaidLeaveSet();
	
	/** 積立年休設定 */
	RetentionYearlySettingRepository getRetentionYearlySet();
	
	/** 特別休暇設定 */
	//SpecialHolidayRepository getSpecialHolidaySet();
	
	/** 代休時間設定の取得 */
	//CompensatoryOccurrenceSettingGetMemento getCompensatoryOccurrenceSet();
	
	/** 回数集計の取得 */
	TotalTimesRepository getTotalTimes();
	
	/** 週開始の取得 */
	GetWeekStart getGetWeekStart();
}
