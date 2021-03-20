package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

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
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
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
 * 実装：期間中の子の看護休暇残数を取得
 * @author yuri_tamakoshi
 */
public class GetRemainingNumberChildCareService {

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
	public AggrResultOfChildCareNurse getChildCareRemNumWithinPeriod(
			String companyId,
			String employeeId,
			DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			Optional<TempChildCareNurseManagement> tempChildCareDataforOverWriteList,
			Optional<AggrResultOfChildCareNurse> prevChildCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			CacheCarrier cacheCarrier,
			Require require) {

		// INPUT．Require子の看護介護休暇設定を取得
		NursingLeaveSetting nursingLeaveSetting = require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing);

		// 子の看護休暇の管理区分を確認する
		if (!nursingLeaveSetting.isManaged()) {
			// デフォルトで返す
			return new AggrResultOfChildCareNurse();
		}
		// 休暇の集計期間から入社前、退職後を除く
		EmployeeImport empInfo = require.findByEmpId(employeeId);
		Optional<DatePeriod> confirmLeavePeriod = ConfirmLeavePeriod.sumPeriod(period, empInfo);

		if(!confirmLeavePeriod.isPresent()) {
			// デフォルトで返す
			return new AggrResultOfChildCareNurse();
		}

		// 集計開始日時点の子の看護情報を作成
		ChildCareNurseUsedNumber startUsed = aggregateStartDateChildCareInfo(companyId, employeeId,
				confirmLeavePeriod.get(),
				performReferenceAtr,
				criteriaDate,
				isOverWrite,
				tempChildCareDataforOverWriteList,
				prevChildCareLeave,
				createAtr,
				periodOverWrite,
				NursingCategory.ChildNursing,
				cacheCarrier,
				require);

		// 暫定子の看護管理データを取得
		List<TempChildCareNurseManagement> tempChildCareManagement = tempChildCareNurseManagement(employeeId, confirmLeavePeriod.get(), isOverWrite,
																													tempChildCareDataforOverWriteList, performReferenceAtr, createAtr, periodOverWrite, require);

		// 子の看護集計期間を作成
		AggregateChildCareNurse createAggregatePeriod = AggregateChildCareNurse.createAggregatePeriod(confirmLeavePeriod.get(), tempChildCareManagement, NursingCategory.ChildNursing, require);

		// 消化と残数を求める
		AggrResultOfChildCareNurse createAggrResult = createAggregatePeriod.createAggrResult(companyId, employeeId, confirmLeavePeriod.get(), criteriaDate, startUsed,NursingCategory.ChildNursing, require);

		// 子の看護休暇集計結果を返す
		return createAggrResult;
	}

	/**
	 * 集計開始日時点の子の看護情報を作成
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
	 * @param nursingCategory 介護看護区分
	 * @return 子の看護介護休暇集計結果
	 */
	public ChildCareNurseUsedNumber aggregateStartDateChildCareInfo (String companyId, String employeeId,DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			Optional<TempChildCareNurseManagement> tempChildCareDataforOverWriteList,
			Optional<AggrResultOfChildCareNurse> prevChildCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			NursingCategory nursingCategory,
			CacheCarrier cacheCarrier,
			Require require) {

		// 集計開始日時点の前回集計結果が存在するかチェック
		if (prevChildCareLeave.isPresent()) {
			// 前回の子の看護休暇集計結果．期間終了日の翌日時点の使用数を返す
			return prevChildCareLeave.get().getAsOfPeriodEnd();
		}
		// 休暇残数を計算する締め開始日を取得する
		GeneralDate closureStart = GetRemNumClosureStart.closureDate(employeeId, cacheCarrier, require);

		// 社員の子の看護使用数を取得
		ChildCareNurseUsedNumber childCareEmployeeUsedNumber = childCareEmployeeUsedNumber(companyId, employeeId,period,
																																									performReferenceAtr,
																																									criteriaDate,
																																									isOverWrite,
																																									tempChildCareDataforOverWriteList,
																																									prevChildCareLeave,
																																									createAtr,
																																									periodOverWrite,
																																									closureStart,
																																									cacheCarrier,
																																									require);

		// 「子の看護介護使用数」を返す
		return childCareEmployeeUsedNumber;
	}
	/**
	 * 社員の子の看護使用数を取得
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
	 * @param closureStartDate 締め開始日
	 * @return ChildCareNurseUsedNumber 子の看護介護使用数
	 */
	public ChildCareNurseUsedNumber childCareEmployeeUsedNumber(String companyId, String employeeId,DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			Optional<TempChildCareNurseManagement> tempChildCareDataforOverWriteList,
			Optional<AggrResultOfChildCareNurse> prevChildCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			GeneralDate closureStartDate,
			CacheCarrier cacheCarrier,
			Require require) {

		// 子の看護介護使用数
		ChildCareNurseUsedNumber childCareNurseUsedNumber = new ChildCareNurseUsedNumber();

		// 取得した締め開始日とパラメータ「集計開始日」を比較
		// ===締め開始日<パラメータ「集計開始日」
		if(closureStartDate.before(period.start())) {
			// 開始日までの子の看護休暇使用数を計算
			AggrResultOfChildCareNurse getChildCareRemNumWithinPeriod = getChildCareRemNumWithinPeriod(companyId, employeeId,period,
					performReferenceAtr,
					criteriaDate,
					isOverWrite,
					tempChildCareDataforOverWriteList,
					prevChildCareLeave,
					createAtr,
					periodOverWrite,
					cacheCarrier,
					require);

			// 期間終了日の翌日時点の使用数を返す
			return getChildCareRemNumWithinPeriod.getAsOfPeriodEnd();

		}else {
			// ドメインモデル「子の看護休暇使用数データ」を取得
			childCareNurseUsedNumber = require.childCareNurseUsedNumber(employeeId);
			return childCareNurseUsedNumber;
		}
	}

	/**
	 * 暫定子の看護管理データ
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param isOverWrite 上書きフラグ(Optional)
	 * @param tempChildCareDataforOverWriteList List<上書き用の暫定管理データ>(Optional)
	 * @param performReferenceAtr 実績のみ参照区分(月次モード orその他)
	 * @param createAtr 作成元区分(Optional)
	 * @param periodOverWrite 上書き対象期間(Optional)
	 * @return 暫定子の看護管理データ
	 */
	public List<TempChildCareNurseManagement> tempChildCareNurseManagement(String employeeId,DatePeriod period,
			Optional<Boolean> isOverWrite,
			Optional<TempChildCareNurseManagement> tempChildCareDataforOverWriteList,
			InterimRemainMngMode performReferenceAtr,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			Require require) {

		// 暫定子の看護介護管理データ
		List<TempChildCareNurseManagement> interimDate = new ArrayList<>();

		// 実績のみ参照区分を確認
		if (performReferenceAtr == InterimRemainMngMode.OTHER) {
			// 暫定子の看護管理データを取得
			interimDate = require.tempChildCareManagement(employeeId , period , RemainType.CHILDCARE);
		}

		// 上書きフラグを確認
		if (isOverWrite.isPresent()) {
			// ※残数共通処理にする必要あり：一時対応
			// 上書き用暫定残数データで置き換える
			//	ドメインモデル「暫定子の看護介護管理データ」．作成元区分 = パラメータ「作成元区分」
			//	パラメータ「上書き対象期間．開始日」 <= ドメインモデル「暫定子の看護介護管理データ」．年月日 <= パラメータ「上書き対象期間．終了日」
			val noOverwriteRemains = interimDate.stream().filter(c -> !periodOverWrite.get().contains(c.getYmd())).collect(Collectors.toList()); //上書き用の暫定管理データから上書対象でない暫定データを退避
			noOverwriteRemains.add(tempChildCareDataforOverWriteList.get());
			return noOverwriteRemains;
		}
		// 暫定子の看護管理データを返す
		return interimDate;
	}

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

		// 年休の契約時間を取得する（会社ID、社員ID、基準日）
		LaborContractTime contractTime(String companyId, String employeeId,  GeneralDate criteriaDate);

		// 子の看護休暇使用数データを取得（社員ID）
		ChildCareNurseUsedNumberData childCareNurseUsedNumber(String employeeId);

		//	介護対象管理データ（家族ID）
		CareManagementDate careData(String familyID);

		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分）
		NursingCareLeaveRemainingInfo upperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory);
	}
}
