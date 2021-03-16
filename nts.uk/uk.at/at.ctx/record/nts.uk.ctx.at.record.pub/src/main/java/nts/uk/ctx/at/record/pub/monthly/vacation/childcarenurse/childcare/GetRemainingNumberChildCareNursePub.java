package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.GetRemainingNumberChildCareService;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

/**
 * 期間中の子の看護休暇残数を取得
 * @author yuri_tamakoshi
 */

public interface  GetRemainingNumberChildCareNursePub {

	/**
	 * 期間中の子の看護休暇残数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 集計期間
	 * @param performReferenceAtr 実績のみ参照区分(月次モード orその他)
	 * @param criteriaDate 基準日
	 * @param isOverWrite 上書きフラグ(Optional)
	 * @param tempChildCareDataforOverWriteList List<上書き用の暫定管理データ>(Optional)
	 * @param prevChildCareLeave 前回の子の看護休暇の集計結果<Optional>
	 * @param createAtr 作成元区分(Optional)
	 * @param periodOverWrite 上書き対象期間(Optional)
	 * @return 子の看護介護休暇集計結果
	 */
	 // RequestList206
	ChildCareNursePeriodExport getChildCareRemNumWithinPeriod(String companyId, String employeeId,DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			Optional<TempChildCareNurseManagement> tempChildCareDataforOverWriteList,
			Optional<AggrResultOfChildCareNurse> prevChildCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			CacheCarrier cacheCarrier,
			Require require);

	public static interface Require extends GetRemainingNumberChildCareService.Require{

		// Imported(就業)「社員」を取得する
		EmployeeImport findByEmpId(String empId);

		// 介護看護休暇設定を取得する（会社ID、介護看護区分）
		NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory);

		// 暫定子の看護管理データを取得する（社員ID、年月日、残数種類）
		List<TempChildCareNurseManagement> tempChildCareManagement(String employeeId, DatePeriod ymd, RemainType remainType);

		// 子の看護・介護休暇基本情報を取得する（社員ID）
		NursingCareLeaveRemainingInfo employeeInfo(String employeeId);

		// 会社の年休設定を取得する（会社ID）
		AnnualPaidLeaveSetting annualLeaveSet(String companyId);

		// 社員の契約時間を取得する（社員ID、基準日）
		LaborContractTime empContractTime(String employeeId, GeneralDate criteriaDate );

		// 年休の契約時間を取得する（会社ID、社員ID、基準日）
		LaborContractTime contractTime(String companyId, String employeeId,  GeneralDate criteriaDate);

		// 子の看護休暇使用数データを取得（社員ID）
		ChildCareNurseUsedNumberData childCareNurseUsedNumber(String employeeId);

		// 社員IDが一致する家族情報を取得（社員ID）
		List<FamilyInfo> familyInfo(String employeeId);

		// 介護対象管理データ（家族ID）
		CareManagementDate careData(String familyID);

		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分）
		NursingCareLeaveRemainingInfo upperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory);
	}
}
