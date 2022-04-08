package nts.uk.ctx.exio.dom.input.domain;

import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.attendance.worktime.WorktimeByWorkplaceCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.AffClassHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.CardNumberCanonicalaization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.AffCompanyHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.AffJobTitleHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.AffWorkplaceHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.EmploymentHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.ShortWorkTimeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.WorkConditionCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.employeebasic.EmployeeBasicCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.annualleave.AnnualLeaveRemainingCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.annualleave.EmployeeAnnualLeaveSettingCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.annualleave.MaxAnnualLeaveCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.compensatory.CompensatoryHolidayCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.compensatory.HolidayWorkCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.substitute.SubstituteHolidayCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.substitute.SubstituteWorkCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special.SpecialHolidayGrantRemainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special.SpecialHolidayGrantSettingCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.stock.StockHolidayRemainingCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.TempAbsenceHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.EmploymentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace.WorkplaceCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.EquipmentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.EquipmentClassiicationCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.StampCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.EnterpriseStampCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.TaskCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.TaskChildCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.smile.SmileTempAbsenceHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ClassificationCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.task.TaskAssignEmpCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.task.TaskAssignWkpCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.WorkLocationCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.WorkLocationIpCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.shift.businesscalendar.PublicHolidayCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.EmployeeContactCanonicalization;

/**
 * 受入グループID
 * 複数ドメインが設定されている場合、このEnumの定義順に受入処理される
 */
@RequiredArgsConstructor
public enum ImportingDomainId {

	/** 雇用マスタ */
	EMPLOYMENT(11, EmploymentCanonicalization::new),

	/** 分類マスタ　*/
	CLASSIFICATION(12, ClassificationCanonicalization::new),

	/** 職場マスタ */
	WORKPLACE(13, WorkplaceCanonicalization::new),

	/** 作業マスタ */
	TASK(30, TaskCanonicalization::new),

	/** 下位作業マスタ */
	TASK_CHILD(31, TaskChildCanonicalization::new),

	/** 設備マスタ */
	EQUIPMENT(32, EquipmentCanonicalization::new),

	/** 設備分類マスタ */
	EQUIPMENT_CLASSIFICATION(33, EquipmentClassiicationCanonicalization::new),

	/** 勤務場所マスタ*/
	WORK_LOCATION(36, WorkLocationCanonicalization::new),

	/** 勤務場所IPマスタ*/
	WORK_LOCATION_IP(37, WorkLocationIpCanonicalization::new),

	/** 職場別就業時間帯*/
	WORKTIME_BY_WORKSPACE(42, WorktimeByWorkplaceCanonicalization::new),

	/** 祝日マスタ*/
	PUBLIC_HOLIDAY(52, PublicHolidayCanonicalization::new),

	/** 個人基本情報 */
	EMPLOYEE_BASIC(100, EmployeeBasicCanonicalization::new),

	/** 入社退職履歴 */
	AFF_COMPANY_HISTORY(101, AffCompanyHistoryCanonicalization::new),

	/** 所属雇用履歴 */
	EMPLOYMENT_HISTORY(102, EmploymentHistoryCanonicalization::new),

	/** 所属職場履歴 **/
	AFF_WORKPLACE_HISTORY(103, AffWorkplaceHistoryCanonicalization::new),

	/** 所属職位履歴 */
	JOBTITLE_HISTORY(104, AffJobTitleHistoryCanonicalization::new),

	/** 所属分類履歴 */
	CLASSIFICATION_HISTORY(105, AffClassHistoryCanonicalization::new),

	/** 休職休業履歴 */
	TEMP_ABSENCE_HISTORY(107, TempAbsenceHistoryCanonicalization::new),

	/** 短時間勤務 */
	SHORT_WORK_TIME(108, ShortWorkTimeCanonicalization::new),

	/** 労働条件*/
	WORKING_CONDITION(109, WorkConditionCanonicalization::new),

	/** カードNO */
	CARD_NO(110, CardNumberCanonicalaization::new),

	/** 社員の年休付与設定*/
	EMPLOYEE_ANNUAL_LEAVE_SETTING(111, EmployeeAnnualLeaveSettingCanonicalization::new),

	/** 年休上限データ*/
	MAX_ANNUAL_LEAVE(112, MaxAnnualLeaveCanonicalization::new),

	/** 年休付与残数データ*/
	ANNUAL_LEAVE_REMAINING(114, AnnualLeaveRemainingCanonicalization::new),

	/** 積立休暇付与残数データ*/
	STOCK_HOLIDAY_REMAINING(115, StockHolidayRemainingCanonicalization::new),

	/** 社員の特別休暇付与設定 */
	EMPLOYEE_SPECIAL_HOLIDAY_GRANT_SETTING(116, SpecialHolidayGrantSettingCanonicalization::new),

	/** 特別休暇付与残数データ */
	SPECIAL_HOLIDAY_GRANT_REMAIN(117, SpecialHolidayGrantRemainCanonicalization::new),

	/** 振休管理データ */
	SUBSTITUTE_HOLIDAY(118, SubstituteHolidayCanonicalization::new),

	/** 振出管理データ */
	SUBSTITUTE_WORK(119, SubstituteWorkCanonicalization::new),

	/** 代休管理データ */
	COMPENSATORY_HOLIDAY(120, CompensatoryHolidayCanonicalization::new),

	/** 休出管理データ */
	HOLIDAY_WORK(121, HolidayWorkCanonicalization::new),

	/** 打刻データ */
	STAMP(130, StampCanonicalization::new),

	/** 打刻データE版 */
	STAMP_ENTERPRISE(131, EnterpriseStampCanonicalization::new),

	/** 社員連絡先 */
	EMPLOYEE_CONTACT(132, EmployeeContactCanonicalization::new),

	/** 職場別作業の絞込 */
	TASK_ASSIGN_WORKPLACE(143, TaskAssignWkpCanonicalization::new),

	/** 社員別作業の絞込 */
	TASK_ASSIGN_EMPLOYEE(144, TaskAssignEmpCanonicalization::new),

	/** Smile休職情報 */
	TEMP_ABSENCE_HISTORY_SMILE(992, SmileTempAbsenceHistoryCanonicalization::new),

	;

	public final int value;

	private final Supplier<DomainCanonicalization> createCanonicalization;

	public static ImportingDomainId valueOf(int value) {
		return EnumAdaptor.valueOf(value, ImportingDomainId.class);
	}

	public DomainCanonicalization createCanonicalization() {
		return createCanonicalization.get();
	}
}
