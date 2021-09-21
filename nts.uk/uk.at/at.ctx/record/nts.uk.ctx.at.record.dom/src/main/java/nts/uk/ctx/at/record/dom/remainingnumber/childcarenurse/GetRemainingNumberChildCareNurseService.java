package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse;

import java.util.List;
import java.util.Optional;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.export.GetRemNumClosureStart;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggregateChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggregateChildCareNurseWork;

/**
 * 実装：期間中の子の看護休暇残数を取得
 * @author yuri_tamakoshi
 */
public class GetRemainingNumberChildCareNurseService {


	public static interface Require extends AggregateChildCareNurse.Require, AggregateChildCareNurseWork.RequireM1, GetRemNumClosureStart.Require{

		// Imported(就業)「社員」を取得する
		EmployeeImport findByEmpId(String empId);

		// 社員IDが一致する家族情報を取得（社員ID）
		List<FamilyInfo> familyInfo(String employeeId);

		// 暫定子の看護管理データを取得する（社員ID、年月日）
		List<TempChildCareManagement> tempChildCareManagement(
				String employeeId, DatePeriod ymd);
		// 暫定介護管理データを取得する（社員ID、年月日）
		List<TempCareManagement> tempCareManagement(
				String employeeId, DatePeriod ymd);

		// 介護看護休暇設定を取得する（会社ID、介護看護区分）
		NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory);

		// 子の看護・介護休暇基本情報を取得する（社員ID）
		Optional<NursingCareLeaveRemainingInfo> employeeInfo(String employeeId, NursingCategory nursingCategory);

		// 子の看護休暇基本情報を取得する（社員ID）
		Optional<ChildCareLeaveRemainingInfo> childCareLeaveEmployeeInfo(String employeeId);
		//介護休暇基本情報を取得する（社員ID）
		Optional<CareLeaveRemainingInfo> careLeaveEmployeeInfo(String employeeId);

		// 子の看護休暇使用数データを取得（社員ID）
		Optional<ChildCareUsedNumberData> childCareUsedNumber(String employeeId);

		// 介護休暇使用数データを取得（社員ID）
		Optional<CareUsedNumberData> careUsedNumber(String employeeId);

		//	介護対象管理データ（家族ID）
		Optional<CareManagementDate> careData(String familyID);
	}
}
