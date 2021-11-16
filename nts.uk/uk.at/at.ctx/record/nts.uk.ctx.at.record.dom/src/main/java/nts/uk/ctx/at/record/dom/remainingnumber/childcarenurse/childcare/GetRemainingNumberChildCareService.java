package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.GetRemainingNumberChildCareNurseService.Require;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.export.GetRemNumClosureStart;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.ConfirmLeavePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;

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
	public static AggrResultOfChildCareNurse getChildCareRemNumWithinPeriod(
			String companyId,
			String employeeId,
			DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			List<TempChildCareManagement> tempChildCareDataforOverWriteList,
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
		List<TempChildCareNurseManagement> tempChildCareManagementList =
				tempChildCareManagement(employeeId, confirmLeavePeriod.get(), isOverWrite,
					tempChildCareDataforOverWriteList, performReferenceAtr, createAtr, periodOverWrite, require)
					.stream().map(mapper->((TempChildCareNurseManagement)mapper)).collect(Collectors.toList());

		// 子の看護集計期間を作成
		AggregateChildCareNurse createAggregatePeriod
			= AggregateChildCareNurse.createAggregatePeriod(
					confirmLeavePeriod.get(), tempChildCareManagementList, NursingCategory.ChildNursing, require);

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
	public static ChildCareNurseUsedNumber aggregateStartDateChildCareInfo (String companyId, String employeeId,DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			List<TempChildCareManagement> tempChildCareDataforOverWriteList,
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
		ChildCareNurseUsedNumber childCareEmployeeUsedNumber
				= childCareEmployeeUsedNumber(companyId, employeeId,period,
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
	public static ChildCareNurseUsedNumber childCareEmployeeUsedNumber(
			String companyId,
			String employeeId,
			DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			List<TempChildCareManagement> tempChildCareDataforOverWriteList,
			Optional<AggrResultOfChildCareNurse> prevChildCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			GeneralDate closureStartDate,
			CacheCarrier cacheCarrier,
			Require require) {

		// 子の看護介護使用数
		Optional<ChildCareUsedNumberData> childCareUsedNumberData = Optional.empty();

		// 取得した締め開始日とパラメータ「集計開始日」を比較
		// ===締め開始日<パラメータ「集計開始日」
		if(closureStartDate.before(period.start())) {

			DatePeriod forGetStartRemPeriod = new DatePeriod(closureStartDate, period.start().addDays(-1));

			// 開始日までの子の看護休暇使用数を計算
			AggrResultOfChildCareNurse getChildCareRemNumWithinPeriod
				= getChildCareRemNumWithinPeriod(
						companyId,
						employeeId,
						forGetStartRemPeriod,
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
			childCareUsedNumberData = require.childCareUsedNumber(employeeId);
			if ( childCareUsedNumberData.isPresent() ) {
				return childCareUsedNumberData.get();
			} else {
				return new ChildCareUsedNumberData(employeeId);
			}
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
	public static List<TempChildCareManagement> tempChildCareManagement(String employeeId,DatePeriod period,
			Optional<Boolean> isOverWrite,
			List<TempChildCareManagement> tempChildCareDataforOverWriteList,
			InterimRemainMngMode performReferenceAtr,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			Require require) {

		// 暫定子の看護介護管理データ
		List<TempChildCareManagement> interimDate = new ArrayList<>();

		// 実績のみ参照区分を確認
		if (performReferenceAtr == InterimRemainMngMode.OTHER) {
			// 暫定子の看護管理データを取得
			interimDate = require.tempChildCareManagement(employeeId , period);
		}

		// 上書きフラグを確認
		if (isOverWrite.orElse(false) && periodOverWrite.isPresent()) {
			// ※残数共通処理にする必要あり：一時対応
			// 上書き用暫定残数データで置き換える
			//	ドメインモデル「暫定子の看護介護管理データ」．作成元区分 = パラメータ「作成元区分」
			//	パラメータ「上書き対象期間．開始日」 <= ドメインモデル「暫定子の看護介護管理データ」．年月日 <= パラメータ「上書き対象期間．終了日」
			List<TempChildCareManagement> noOverwriteRemains =
					interimDate
					.stream()
					.filter(c -> !periodOverWrite.get().contains(c.getYmd()))
					.collect(Collectors.toList()); //上書き用の暫定管理データから上書対象でない暫定データを退避
			noOverwriteRemains.addAll(tempChildCareDataforOverWriteList);
			return noOverwriteRemains;
		
		}
		// 暫定子の看護管理データを返す
		return interimDate;
	}
}
