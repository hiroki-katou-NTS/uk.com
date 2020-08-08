package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.TempAbsenceFrameNo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;

/**
 * 社員の予定管理状態
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * 
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class ScheManaStatuTempo {

	/** 社員ID **/
	private final String employeeID;

	/** 年月日 **/
	private final GeneralDate date;

	/** 予定管理状態 **/
	private final ScheManaStatus scheManaStatus;

	/** 休業枠NO **/
	private final Optional<TempAbsenceFrameNo> optTempAbsenceFrameNo;
	/** 雇用コード **/
	private final Optional<EmploymentCode> optEmploymentCd;

	/**
	 * [C-1] 作成する
	 * 
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	public static ScheManaStatuTempo create(Require require, String employeeID, GeneralDate date) {
		// ScheManaStatuTempo result = new ArrayList<>();
		
		boolean enrolled = enrolled(require, employeeID, date);
		/*
		 * if not [S-1] 在籍中か( require, 社員ID, 年月日 )
		 * 
		 * @予定管理状態 = 予定管理状態．在籍していない return this
		 */
		if (!enrolled) {
			return new ScheManaStatuTempo(employeeID, date, ScheManaStatus.NOT_ENROLLED,
					Optional.empty(), Optional.empty());		
		}
		/*
		 * @雇用コード = [S-2] 雇用コードを取得する( require, 社員ID, 年月日 ) if @雇用コード.isEmpty()
		 * 
		 * @予定管理状態 = 予定管理状態．データ不正 return this
		 */
		Optional<EmploymentCode> zEmpployeeCd = ScheManaStatuTempo.getEmplomentCd(require, employeeID, date);
		if (!zEmpployeeCd.isPresent()) {
			return new ScheManaStatuTempo(employeeID, date, ScheManaStatus.INVALID_DATA, Optional.empty(),
					Optional.empty());
		}
		Optional<ManageAtr> zScheduleManaCategory = getManageAtr(require, employeeID, date);

		/*
		 * $予定管理区分 = [S-3] 予定管理区分を取得する( require, 社員ID, 年月日 ) if
		 * $予定管理区分.isEmpty()
		 * 
		 * @予定管理状態 = 予定管理状態．データ不正 return this else if $予定管理区分 == 予定管理区分．管理しない
		 * 
		 * @予定管理状態 = 予定管理状態．予定管理しない return this
		 */
		if (!zScheduleManaCategory.isPresent()) {
			return new ScheManaStatuTempo(employeeID, date, ScheManaStatus.INVALID_DATA, Optional.empty(),
					Optional.empty());
		
		} else if (zScheduleManaCategory.get().value == ManageAtr.NOTUSE.value) {
			return new ScheManaStatuTempo(employeeID, date, ScheManaStatus.DO_NOT_MANAGE_SCHEDULE,
					Optional.empty(), Optional.empty());
		}
		/*
		 * if [S-4] 休職中か( require, 社員ID, 年月日 )
		 * 
		 * @予定管理状態 = 予定管理状態．休職中 return this
		 */
		if (onLeave(require, employeeID, date)) {
			return new ScheManaStatuTempo(employeeID, date, ScheManaStatus.ON_LEAVE, Optional.empty(),
					Optional.empty());
		}
		/*
		 * @休業枠NO = [S-5] 休業枠NOを取得する( require, 社員ID, 年月日 ) if
		 * not @休業枠NO.isEmpty()
		 * 
		 * @予定管理状態 = 予定管理状態．休業中 return this
		 */
		Optional<TempAbsenceFrameNo> optTempAbsenceFrameNo = getTempAbsenceFrameNo(require, employeeID, date);
		if (optTempAbsenceFrameNo.isPresent()) {
			return  new ScheManaStatuTempo(employeeID, date, ScheManaStatus.CLOSED, Optional.empty(),
					Optional.empty());
		
		}
		return new ScheManaStatuTempo(employeeID, date, ScheManaStatus.SCHEDULE_MANAGEMENT, Optional.empty(), Optional.empty());
	}

	/**
	 * [S-1] 在籍中か
	 * 
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	private static boolean enrolled(Require require, String employeeID, GeneralDate date) {

		DatePeriod datePeriod = new DatePeriod(date, date);

		//return require.在籍期間を取得する( 社員ID, 年月日 ).isPresent()
		boolean reusult = require.getAffCompanyHistByEmployee(Arrays.asList(employeeID), datePeriod).isEmpty();
		return !reusult;
	}

	/**
	 * [S-2] 雇用コードを取得する
	 * 
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	private static Optional<EmploymentCode> getEmplomentCd(Require require, String employeeID, GeneralDate date) {
;
		DatePeriod datePeriod = new DatePeriod(date, date);
		// $雇用履歴項目 = require.雇用履歴を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ): findFirst
		// ・・
		List<EmploymentPeriodImported> lstEmpHistItem = require.getEmploymentHistory(Arrays.asList(employeeID), datePeriod);
		if(!lstEmpHistItem.isEmpty())
		{
			EmploymentPeriodImported employmentPeriod  = lstEmpHistItem.get(0);
			return 	Optional.ofNullable(new EmploymentCode(employmentPeriod.getEmploymentCd()));
		}
		return Optional.empty();
	}

	/**
	 * [S-3] 予定管理区分を取得する
	 * 
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	private static Optional<ManageAtr> getManageAtr(Require require, String employeeID, GeneralDate date) {
		Optional<WorkingConditionItem> zWorkingConditionIDtem = require.getBySidAndStandardDate(employeeID, date);
		return zWorkingConditionIDtem.map(c -> c.getScheduleManagementAtr());
	}

	/**
	 * [S-4] 休職中か
	 * 
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	private static boolean onLeave(Require require, String employeeID, GeneralDate date) {
		DatePeriod datePeriod = new DatePeriod(date, date);
		List<EmployeeLeaveJobPeriodImport> lstEmployeeLeaveJobPeriodImport = require.getByDatePeriod(Arrays.asList(employeeID), datePeriod);
		boolean result = lstEmployeeLeaveJobPeriodImport.isEmpty();		
		return (!result);

	}

	/**
	 * [S-5] 休業枠NOを取得する
	 * 
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return Optional<休職休業枠NO>
	 */
	private static Optional<TempAbsenceFrameNo> getTempAbsenceFrameNo(Require require, String employeeID,
			GeneralDate date) {

		DatePeriod datePeriod = new DatePeriod(date, date);
		List<EmpLeaveWorkPeriodImport> lstEmpLeaveWorkPeriodImport = require.specAndGetHolidayPeriod(Arrays.asList(employeeID), datePeriod);
		if(lstEmpLeaveWorkPeriodImport.isEmpty()){
			return Optional.empty();
		}
		EmpLeaveWorkPeriodImport data = lstEmpLeaveWorkPeriodImport.get(0);
		return Optional.ofNullable(data.getTempAbsenceFrNo());
	}

	public static interface Require  {

		// @Inject
		// private WorkingConditionRepository repo;

		/**
		 * [R-1] 在籍期間を取得する( 社員ID, 年月日 ) : Optional
		 * 社員の所属会社履歴Adapter.期間を指定して在籍期間を取得する
		 */
		List<EmpEnrollPeriodImport> getAffCompanyHistByEmployee(List<String> sids, DatePeriod datePeriod);

		/**
		 * [R-2] 労働条件履歴を取得する 労働条件Repository.社員を指定して年月日時点の履歴項目を取得する
		 */

		// Optional<WorkingCondition> repo.getBy getBySidAndStandardDate(String
		// companyId, String employeeId, GeneralDate baseDate);
		Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate);

		/**
		 * [R-3] 休職期間を取得する( 社員ID, 年月日 ) : Optional
		 * 社員の休職履歴Adapter.期間を指定して休職期間を取得する( list: 社員ID, 期間: 年月日 )
		 * 
		 */
		List<EmployeeLeaveJobPeriodImport> getByDatePeriod(List<String> lstEmpID, DatePeriod datePeriod);

		/**
		 * 
		 * [R-4] 休業期間を取得する(Get ''closedPeriod/khoảng thời gian nghỉ kinh
		 * doanh'') 社員の休業履歴Adapter.期間を指定して休業期間を取得する( 社員IDリスト, 期間 )
		 * 
		 * @param lstEmpID
		 * @param datePeriod
		 * @return
		 */
		List<EmpLeaveWorkPeriodImport> specAndGetHolidayPeriod(List<String> lstEmpID, DatePeriod datePeriod);

		/**
		 * [R-5] 雇用履歴を取得する(Get EmploymentHistory)
		 * 社員の雇用履歴Adapter.期間を指定して雇用履歴を取得する( 社員IDリスト, 期間 )
		 * 
		 * @param lstEmpID
		 * @param datePeriod
		 * @return List EmploymentPeriod
		 */
		List<EmploymentPeriodImported> getEmploymentHistory(List<String> lstEmpID, DatePeriod datePeriod);

	}

}
