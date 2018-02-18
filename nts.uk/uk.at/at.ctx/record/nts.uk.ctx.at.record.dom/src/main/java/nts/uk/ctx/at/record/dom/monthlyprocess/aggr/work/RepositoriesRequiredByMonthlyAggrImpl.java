package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.GetAggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthlyRepository;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.GetOfStatutoryWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.GetWeekStart;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 月別集計が必要とするリポジトリ実装
 * @author shuichu_ishida
 */
@Stateless
@Getter
public class RepositoriesRequiredByMonthlyAggrImpl implements RepositoriesRequiredByMonthlyAggr {

	/** 所属職場履歴の取得 */
	@Inject
	public AffWorkplaceAdapter affWorkplaceAdapter;

	/** 所属雇用履歴の取得 */
	@Inject
	public SyEmploymentAdapter syEmployment;
	
	/** 日別実績の勤怠時間の取得 */
	@Inject
	public AttendanceTimeRepository attendanceTimeOfDaily;
	
	/** 日別実績の勤務情報の取得 */
	@Inject
	public WorkInformationRepository workInformationOfDaily;

	/** 日別実績の出退勤の取得 */
	@Inject
	public TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;
	
	/** 日別実績の臨時出退勤の取得 */
	@Inject
	public TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDaily;
	
	/** 日別実績の特定日区分の取得 */
	@Inject
	public SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDaily;
	
	/** 勤務情報の取得 */
	@Inject
	public WorkTypeRepository workType;
	
	/** 所定時間設定の取得 */
	@Inject
	public PredetemineTimeSettingRepository predetermineTimeSet;
	
	/** 社員の日別積実績エラー一覧 */
	@Inject
	public EmployeeDailyPerErrorRepository employeeDailyError;
	
	/** 月別実績の勤怠時間の取得 */
	@Inject
	public AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthly;
	
	/** 月別実績集計設定の取得 */
	@Inject
	public GetAggrSettingMonthly aggrSettingMonthly;

	/** 月次集計の法定内振替順設定の取得 */
	@Inject
	public LegalTransferOrderSetOfAggrMonthlyRepository legalTransferOrderSetOfAggrMonthly;
	
	/** 月別実績の縦計方法の取得 */
	//
	
	/** 月別実績の給与項目カウントの取得 */
	@Inject
	public PayItemCountOfMonthlyRepository payItemCountOfMonthly;
	
	/** 法定労働時間の取得 */
	@Inject
	public GetOfStatutoryWorkTime getOfStatutoryWorkTime;
	
	/** 時間外超過設定の取得 */
	@Inject
	public OutsideOTSettingRepository outsideOTSet;
	
	/** 休日加算設定 */
	@Inject
	public HolidayAddtionRepository holidayAddition;
	
	/** 年休設定 */
	@Inject
	public AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	
	/** 積立年休設定 */
	@Inject
	public RetentionYearlySettingRepository retentionYearlySet;
	
	/** 特別休暇設定 */
	//@Inject
	//public SpecialHolidayRepository specialHolidaySet;
	
	/** 代休時間設定の取得 */
	//@Inject
	//public CompensatoryOccurrenceSettingGetMemento compensatoryOccurrenceSet;
	
	/** 週開始の取得 */
	@Inject
	public GetWeekStart getWeekStart;
}
