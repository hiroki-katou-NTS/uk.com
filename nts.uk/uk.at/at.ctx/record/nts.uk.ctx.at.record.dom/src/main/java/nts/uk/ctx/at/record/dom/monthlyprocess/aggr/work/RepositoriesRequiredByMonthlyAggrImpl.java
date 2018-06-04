package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.WorkTypeOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.workform.flex.MonthlyAggrSetOfFlexRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.period.GetWeekPeriod;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryWorkingHours;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly.GetWeekStart;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.export.GetDeforAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.export.GetFlexAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.export.GetRegularAggrSet;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.GetAttendanceStatus;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.algorithm.GetTimeAndCountFromDailyRecord;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkRepository;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.flex.GetFlexPredWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.GetHolidayWorkAndTransferOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.GetOverTimeAndTransferOrder;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 実装：月別集計が必要とするリポジトリ
 * @author shuichu_ishida
 */
@Stateless
@Getter
public class RepositoriesRequiredByMonthlyAggrImpl implements RepositoriesRequiredByMonthlyAggr {

	/** 社員の取得 */
	@Inject
	public EmpEmployeeAdapter empEmployee;

	/** 労働条件項目の取得 */
	@Inject
	public WorkingConditionItemRepository workingConditionItem;
	/** 労働条件の取得 */
	@Inject
	public WorkingConditionRepository workingCondition;
	/** 所属職場履歴の取得 */
	@Inject
	public AffWorkplaceAdapter affWorkplace;
	/** 所属雇用履歴の取得 */
	@Inject
	public SyEmploymentAdapter syEmployment;
	
	/** 日別実績の勤務種別の取得 */
	@Inject
	public WorkTypeOfDailyPerforRepository workTypeOfDaily;
	/** 日別実績の勤怠時間の取得 */
	@Inject
	public AttendanceTimeRepository attendanceTimeOfDaily;
	/** 日別実績の勤務情報の取得 */
	@Inject
	public WorkInformationRepository workInformationOfDaily;
	/** 日別実績の所属情報の取得 */
	@Inject
	public AffiliationInforOfDailyPerforRepository affiliationInfoOfDaily;
	/** 日別実績の出退勤の取得 */
	@Inject
	public TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;
	/** 日別実績の臨時出退勤の取得 */
	@Inject
	public TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDaily;
	/** 日別実績の特定日区分の取得 */
	@Inject
	public SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDaily;
	/** 日別実績のPCログオン情報 */
	@Inject
	public PCLogOnInfoOfDailyRepo PCLogonInfoOfDaily;
	/** 社員の日別積実績エラー一覧 */
	@Inject
	public EmployeeDailyPerErrorRepository employeeDailyError;
	/** 日別実績の任意項目の取得 */
	@Inject
	public AnyItemValueOfDailyRepo anyItemValueOfDaily;
	
	/** 勤務情報の取得 */
	@Inject
	public WorkTypeRepository workType;
	/** 就業時間帯：共通設定の取得 */
	@Inject
	public GetCommonSet commonSet;
	/** 所定時間設定の取得 */
	@Inject
	public PredetemineTimeSettingRepository predetermineTimeSet;
	/** 締めの取得 */
	@Inject
	private ClosureRepository closure;
	
	/** 日の法定労働時間の取得 */
	@Inject
	public DailyStatutoryWorkingHours dailyStatutoryWorkingHours;
	/** 週・月の法定労働時間の取得*/
	@Inject
	public MonthlyStatutoryWorkingHours monthlyStatutoryWorkingHours;
	
	/** 月別実績の勤怠時間 */
	@Inject
	public AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthly;
	/** 月別実績の任意項目 */
	@Inject
	public AnyItemOfMonthlyRepository anyItemOfMonthly;
	
	/** 集計設定の取得（通常勤務） */
	@Inject
	public GetRegularAggrSet regularAggrSet;
	/** 集計設定の取得（変形労働） */
	@Inject
	public GetDeforAggrSet deforAggrSet;
	/** 集計設定の取得（フレックス） */
	@Inject
	public GetFlexAggrSet flexAggrSet;
	/** フレックス勤務の月別集計設定の取得 */
	@Inject
	public MonthlyAggrSetOfFlexRepository monthlyAggrSetOfFlex;
	/** フレックス勤務所定労働時間取得 */
	@Inject
	public GetFlexPredWorkTimeRepository flexPredWorktime;
	/** 残業・振替の処理順序を取得する */
	@Inject
	public GetOverTimeAndTransferOrder overTimeAndTransferOrder;
	/** 休出・振替の処理順序を取得する */
	@Inject
	public GetHolidayWorkAndTransferOrder holidayWorkAndTransferOrder;
	/** 残業枠の役割 */
	@Inject
	public RoleOvertimeWorkRepository roleOverTimeFrame;
	/** 休出枠の役割 */
	@Inject
	public RoleOfOpenPeriodRepository roleHolidayWorkFrame;
	/** 月次集計の法定内振替順設定の取得 */
	@Inject
	public LegalTransferOrderSetOfAggrMonthlyRepository legalTransferOrderSetOfAggrMonthly;
	/** 休日加算設定 */
	@Inject
	public HolidayAddtionRepository holidayAddition;
	/** 休暇加算設定を取得する */
	@Inject
	public GetVacationAddSet vacationAddSet;
	/** 回数集計 */
	@Inject
	public TotalTimesRepository totalTimes;
	/** 任意項目 */
	@Inject
	public OptionalItemRepository optionalItem;
	
	/** 週開始の取得 */
	@Inject
	public GetWeekStart weekStart;
	/** 週集計期間を取得する */
	@Inject
	public GetWeekPeriod weekPeriod;
	
	/** 時間外超過設定の取得 */
	@Inject
	public OutsideOTSettingRepository outsideOTSet;
	
	/** ドメインサービス：36協定 */
	@Inject
	public AgreementDomainService agreementDomainService;
	/** 36協定運用設定の取得 */
	@Inject
	public AgreementOperationSettingRepository agreementOperationSet;
	/** 36協定年月設定の取得 */
	@Inject
	public AgreementMonthSettingRepository agreementMonthSet;
	
	/** 出勤状態を取得する */
	@Inject
	public GetAttendanceStatus attendanceStatus;
	/** 月別実績の給与項目カウントの取得 */
	@Inject
	public PayItemCountOfMonthlyRepository payItemCountOfMonthly;
	/** 月別実績の丸め設定の取得 */
	@Inject
	public RoundingSetOfMonthlyRepository roundingSetOfMonthly;

	/** 日別実績から回数集計結果を取得する */
	@Inject
	public GetTimeAndCountFromDailyRecord timeAndCountFromDailyRecord;
}
