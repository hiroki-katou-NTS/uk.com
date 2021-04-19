package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.export.GetRemNumClosureStart;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ConfirmLeavePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
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

		// 暫定子の看護管理データを取得する（社員ID、年月日、残数種類）
		List<TempChildCareNurseManagement> tempChildCareManagement(String employeeId, DatePeriod ymd, RemainType remainType);

		// 介護看護休暇設定を取得する（会社ID、介護看護区分）
		NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory);

		// 子の看護・介護休暇基本情報を取得する（社員ID）
		NursingCareLeaveRemainingInfo employeeInfo(String employeeId);

		// 会社の年休設定を取得する（会社ID）
		AnnualPaidLeaveSetting annualLeaveSet(String companyId);

		// 社員の契約時間を取得する（社員ID、基準日）
		LaborContractTime empContractTime(String employeeId, GeneralDate criteriaDate );

		// 子の看護休暇使用数データを取得（社員ID）
		Optional<ChildCareUsedNumberData> childCareUsedNumber(String employeeId);

		// 介護休暇使用数データを取得（社員ID）
		Optional<CareUsedNumberData> careUsedNumber(String employeeId);

		//	介護対象管理データ（家族ID）
		CareManagementDate careData(String familyID);

		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分）
		NursingCareLeaveRemainingInfo upperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory);
	}
}
