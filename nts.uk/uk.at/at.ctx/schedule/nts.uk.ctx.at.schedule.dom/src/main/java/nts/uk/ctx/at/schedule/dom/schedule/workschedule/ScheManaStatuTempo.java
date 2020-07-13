package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.AffCompanyHistSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmploymentPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;

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
		ScheManaStatuTempo manaStatuTempo = null;
		boolean enrolled = enrolled(require, employeeID, date);
		/*
		 * if not [S-1] 在籍中か( require, 社員ID, 年月日 )
		 * 
		 * @予定管理状態 = 予定管理状態．在籍していない return this
		 */
		if (!enrolled) {
			manaStatuTempo = new ScheManaStatuTempo(employeeID, date, ScheManaStatus.SCHEDULE_MANAGEMENT,
					Optional.empty(), Optional.empty());
			return manaStatuTempo;
		}
		/*
		 * @雇用コード = [S-2] 雇用コードを取得する( require, 社員ID, 年月日 ) if @雇用コード.isEmpty()
		 * 
		 * @予定管理状態 = 予定管理状態．データ不正 return this
		 */
		Optional<EmploymentCode> zEmpployeeCd = ScheManaStatuTempo.getEmplomentCd(require, employeeID, date);
		if (!zEmpployeeCd.isPresent()) {
			manaStatuTempo = new ScheManaStatuTempo(employeeID, date, ScheManaStatus.INVALID_DATA, Optional.empty(),
					Optional.empty());
			return manaStatuTempo;
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
			manaStatuTempo = new ScheManaStatuTempo(employeeID, date, ScheManaStatus.INVALID_DATA, Optional.empty(),
					Optional.empty());
			return manaStatuTempo;
		} else if (zScheduleManaCategory.get().value == ManageAtr.NOTUSE.value) {
			manaStatuTempo = new ScheManaStatuTempo(employeeID, date, ScheManaStatus.DO_NOT_MANAGE_SCHEDULE,
					Optional.empty(), Optional.empty());
			return manaStatuTempo;
		}
		/*
		 * if [S-4] 休職中か( require, 社員ID, 年月日 )
		 * 
		 * @予定管理状態 = 予定管理状態．休職中 return this
		 */
		if (onLeave(require, employeeID, date)) {
			manaStatuTempo = new ScheManaStatuTempo(employeeID, date, ScheManaStatus.ON_LEAVE, Optional.empty(),
					Optional.empty());
			return manaStatuTempo;
		}
		/*
		 * @休業枠NO = [S-5] 休業枠NOを取得する( require, 社員ID, 年月日 ) if
		 * not @休業枠NO.isEmpty()
		 * 
		 * @予定管理状態 = 予定管理状態．休業中 return this
		 */
		Optional<TempAbsenceFrameNo> optTempAbsenceFrameNo = getTempAbsenceFrameNo(require, employeeID, date);
		if (optTempAbsenceFrameNo.isPresent()) {
			manaStatuTempo = new ScheManaStatuTempo(employeeID, date, ScheManaStatus.CLOSED, Optional.empty(),
					Optional.empty());
			return manaStatuTempo;
		}

		return manaStatuTempo;
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
		List<String> lst = new ArrayList<>();
		lst.add(0, employeeID);
		DatePeriod datePeriod = new DatePeriod(date, date);

		// return not require.在籍期間を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ).isEmpty()
		boolean reusult = require.getAffCompanyHistByEmployee(lst, datePeriod).isEmpty();

		return (!reusult);
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
		List<String> lst = new ArrayList<>();
		lst.add(0, employeeID);
		DatePeriod datePeriod = new DatePeriod(date, date);
		// $雇用履歴項目 = require.雇用履歴を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ): findFirst
		// ・・
		Optional<EmploymentPeriod> zEmpHistItem = require.getEmploymentHistory(lst, datePeriod).stream().findFirst();
		if (zEmpHistItem.isPresent()) {
			return Optional.of(new EmploymentCode(zEmpHistItem.get().getEmploymentCd()));
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

		Optional<WorkingCondition> zWorkingConditionIDtem = require.getBySidAndStandardDate(employeeID, date);

		if (zWorkingConditionIDtem.isPresent()) {
			// http://192.168.50.4:3000/issues/110588 Chờ QA
			return Optional.empty();
		}
		return Optional.empty();
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
		List<String> lst = new ArrayList<>();
		lst.add(0, employeeID);
		DatePeriod datePeriod = new DatePeriod(date, date);
		List<EmployeeLeaveJobPeriodImport> zEmployeeLeaveJobPeriodImport = require.getByDatePeriod(lst, datePeriod);
		boolean result = zEmployeeLeaveJobPeriodImport.isEmpty();
		// return not require.休職期間を取得する( list: 社員ID, 期間( 年月日, 年月日 ) ).isEmpty()
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
		List<String> lst = new ArrayList<>();
		lst.add(0, employeeID);
		DatePeriod datePeriod = new DatePeriod(date, date);
		Optional<EmpLeaveWorkPeriodImport> zEmpLeaveWorkPeriodImport = require.specAndGetHolidayPeriod(lst, datePeriod)
				.stream().findFirst();
		if (zEmpLeaveWorkPeriodImport.isPresent()) {
			BigDecimal bigDaddy = new BigDecimal(zEmpLeaveWorkPeriodImport.get().getTempAbsenceFrNo());
			return Optional.ofNullable(new TempAbsenceFrameNo(bigDaddy));
		}
		return Optional.empty();
	}

	public static interface Require extends EmpEmployeeAdapter {

		// @Inject
		// private WorkingConditionRepository repo;

		/**
		 * [R-1] 在籍期間を取得する( 社員ID, 年月日 ) : Optional
		 * 社員の所属会社履歴Adapter.期間を指定して在籍期間を取得する
		 */
		List<AffCompanyHistSharedImport> getAffCompanyHistByEmployee(List<String> sids, DatePeriod datePeriod);

		/**
		 * [R-2] 労働条件履歴を取得する 労働条件Repository.社員を指定して年月日時点の履歴項目を取得する
		 */

		// Optional<WorkingCondition> repo.getBy getBySidAndStandardDate(String
		// companyId, String employeeId, GeneralDate baseDate);
		Optional<WorkingCondition> getBySidAndStandardDate(String employeeId, GeneralDate baseDate);

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
		 * @return
		 */
		List<EmploymentPeriod> getEmploymentHistory(List<String> lstEmpID, DatePeriod datePeriod);

	}

}
