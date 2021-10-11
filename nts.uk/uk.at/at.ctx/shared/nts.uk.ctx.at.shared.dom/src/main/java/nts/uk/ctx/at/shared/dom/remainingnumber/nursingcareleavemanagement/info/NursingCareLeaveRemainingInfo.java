package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimitPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimitSplit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

/**
 * 子の看護・介護休暇基本情報
 *
 * @author xuan vinh
 *
 */
@Getter
@Setter
public abstract class NursingCareLeaveRemainingInfo{

	// 社員ID
	private String sId;

	// 介護看護区分
	private NursingCategory leaveType;

	// 使用区分
	private boolean useClassification;

	// 上限設定
	private UpperLimitSetting upperlimitSetting;

	// 本年度上限日数
	private Optional<ChildCareNurseUpperLimit> maxDayForThisFiscalYear;

	// 次年度上限日数
	private Optional<ChildCareNurseUpperLimit> maxDayForNextFiscalYear;

	/**
	 * コンストラクタ
	 */
	public NursingCareLeaveRemainingInfo() {
		maxDayForThisFiscalYear = Optional.empty();
		maxDayForNextFiscalYear = Optional.empty();
	}

	public NursingCareLeaveRemainingInfo(String sId, NursingCategory leaveType, boolean useClassification,
			UpperLimitSetting upperlimitSetting, Optional<ChildCareNurseUpperLimit> maxDayForThisFiscalYear,
			Optional<ChildCareNurseUpperLimit> maxDayForNextFiscalYear) {
		super();
		this.sId = sId;
		this.leaveType = leaveType;
		this.useClassification = useClassification;
		this.upperlimitSetting = upperlimitSetting;
		this.maxDayForThisFiscalYear = maxDayForThisFiscalYear;
		this.maxDayForNextFiscalYear = maxDayForNextFiscalYear;
	}

//	public NursingCareLeaveRemainingInfo(String sId, Optional<ChildCareNurseUpperLimit> maxDayForThisFiscalYear,
//			Optional<ChildCareNurseUpperLimit> maxDayForNextFiscalYear) {
//		super();
//		this.sId = sId;
//		//this.leaveType = EnumAdaptor.valueOf(2, LeaveType.class);
//		this.leaveType = NursingCategory.ChildNursing;
//		this.useClassification = false;
//		this.upperlimitSetting = UpperLimitSetting.FAMILY_INFO;
//		this.maxDayForThisFiscalYear = maxDayForThisFiscalYear;
//		this.maxDayForNextFiscalYear = maxDayForNextFiscalYear;
//	}

	/**
	 * 期間の上限日数を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param criteriaDate 基準日
	 * @param 介護看護休暇設定を取得する（会社ID、介護看護区分）
	 * @param 社員IDが一致する家族情報を取得（社員ID）
	 * @param 介護対象管理データ（家族ID））
	 * @return 上限日数期間（List）
	 */
	public List<ChildCareNurseUpperLimitPeriod> childCareNurseUpperLimitPeriod(String companyId,
			String employeeId, DatePeriod calcPeriod, GeneralDate criteriaDate, RequireM7 require) {

		List<ChildCareNurseUpperLimitSplit> childCareNurseUpperLimitSplit = new ArrayList<>();

		// INPUT．Require．介護看護休暇設定を取得する（会社ID、介護看護区分）
		NursingLeaveSetting nursingLeaveSetting = require.nursingLeaveSetting(companyId, this.leaveType);

		// 次回起算日を計算する期間を作成
		DatePeriod nextCalcPeriod = new DatePeriod(calcPeriod.start().addDays(1), calcPeriod.end().addDays(1));

		// 次回起算日を求める
		GeneralDate nextStartMonthDay = nursingLeaveSetting.getNextStartMonthDay(nextCalcPeriod.start());

		// 期間に次回起算日があるか
		//	===次回起算日を計算する期間．開始日 <=次回起算日 <= 次回起算日を計算する期間．終了日
		if (nextCalcPeriod.contains(nextStartMonthDay)) {
			// 期間．開始日から次回起算日の前日の上限日数を取得List<ChildCareTargetChanged> childCareTargetChanged
			childCareNurseUpperLimitSplit.addAll(childCareNurseUpperLimitSplit(companyId, employeeId,
					new DatePeriod(calcPeriod.start(), nextStartMonthDay.addDays(-1)), criteriaDate, nextStartMonthDay, require));

			// 次回起算日から次回起算日を計算する期間．終了日期間の上限日数を取得
			childCareNurseUpperLimitSplit.addAll(childCareNurseUpperLimitSplit(companyId,employeeId,
						new DatePeriod(nextStartMonthDay, nextCalcPeriod.end()),criteriaDate,nextStartMonthDay, require));
			
			//上限日数分割日から上限日数期間を作成
			return limitSplitTolimitPeriod(new DatePeriod(calcPeriod.start(), nextCalcPeriod.end()),childCareNurseUpperLimitSplit);

		}else {
			// 期間．開始日を分割日に設定
			childCareNurseUpperLimitSplit = childCareNurseUpperLimitSplit(companyId,employeeId,
					calcPeriod ,criteriaDate, nextStartMonthDay,require);
			
			//上限日数分割日から上限日数期間を作成
			return limitSplitTolimitPeriod(new DatePeriod(calcPeriod.start(), nextCalcPeriod.end()),childCareNurseUpperLimitSplit);
		}

	}

	/**
	 * 上限日数を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param criteriaDate 基準日
	 * @param nextStartMonthDay 次回起算日
	 * @param 介護看護休暇設定を取得する（会社ID、介護看護区分）
	 * @param 社員IDが一致する家族情報を取得（社員ID）
	 * @param 介護対象管理データ（家族ID））
	 * @return 子の看護介護使用数
	 */
	public List<ChildCareNurseUpperLimitSplit> childCareNurseUpperLimitSplit (String companyId,
			String employeeId, DatePeriod period,GeneralDate criteriaDate,GeneralDate nextStartMonthDay, RequireM7 require){

		List<ChildCareNurseUpperLimitSplit> childCareNurseUpperLimitSplit = new ArrayList<>();

		// 上限設定を確認
		// ===家族情報を参照：家族情報を参照
		//if (upperlimitSetting == UpperLimitSetting.FAMILY_INFO) {

			// INPUT．Require．介護看護休暇設定を取得する
			//NursingLeaveSetting nursingLeaveSetting = require.nursingLeaveSetting(companyId, this.leaveType);

			// 家族情報から対象人数を履歴で求める
			// ===社員ID←パラメータ「社員ID」
			// ===期間←パラメータ「期間」
			// ===基準日←パラメータ「基準日」
			// ===Require
			//childCareNurseUpperLimitSplit = nursingLeaveSetting.getHistoryCountFromFamilyInfo(employeeId, period, criteriaDate, require);
			
			// 「上限日数分割日（List）」を返す
			//return childCareNurseUpperLimitSplit;
		//}  
		
		// ===個人情報を参照（本年度のみ利用）
		if (upperlimitSetting == UpperLimitSetting.PER_INFO_FISCAL_YEAR){
			// 期間に次回起算日が含まれているか
			if (period.contains(nextStartMonthDay)) {
				// 上限日数分割日に上限日数を設定
				// ===年月日＝パラメータ「期間．開始日」
				// ===上限日数＝次年度上限日数を設定
				if(maxDayForNextFiscalYear.isPresent())
				{
					childCareNurseUpperLimitSplit.add(ChildCareNurseUpperLimitSplit.of(maxDayForNextFiscalYear.get(), period.start()));
				}else{
					childCareNurseUpperLimitSplit.add(ChildCareNurseUpperLimitSplit.of(
							new ChildCareNurseUpperLimit(0), period.start()));
				}
			}else if(!period.contains(nextStartMonthDay)){
				// ===期間に次回起算日が含まれていない
				// ===年月日＝パラメータ「期間．開始日」
				// ===上限日数＝本年度上限日数を設定
				if(maxDayForThisFiscalYear.isPresent()){
					childCareNurseUpperLimitSplit.add(ChildCareNurseUpperLimitSplit.of(maxDayForThisFiscalYear.get(), period.start()));
				}else{
					childCareNurseUpperLimitSplit.add(ChildCareNurseUpperLimitSplit.of(
							new ChildCareNurseUpperLimit(0), period.start()));
				}
			}
			// 「上限日数分割日（List）」を返す
			return childCareNurseUpperLimitSplit;
		}
		// ===個人情報を参照（毎年利用）子の看護・介護休暇基本情報のドメイン修正時再度修正が必要
		if(upperlimitSetting == UpperLimitSetting.PER_INFO_EVERY_YEAR){
			// ===年月日＝パラメータ「期間．開始日」
			// ===上限日数＝本年度上限日数を設定
			if(maxDayForThisFiscalYear.isPresent()){			
				childCareNurseUpperLimitSplit.add(ChildCareNurseUpperLimitSplit.of(maxDayForThisFiscalYear.get(), period.start()));
			}else{
				childCareNurseUpperLimitSplit.add(ChildCareNurseUpperLimitSplit.of(
						new ChildCareNurseUpperLimit(0), period.start()));
			}
			
			// 「上限日数分割日（List）」を返す
			return childCareNurseUpperLimitSplit;
		}
		
		throw new RuntimeException();
	}


	/**
	 * 上限日数分割日から上限日数期間を作成
	 * @param period 期間
	 * @param upperLimitSplit 上限日数分割日
	 * @return 子の看護介護使用数
	 */
	public List<ChildCareNurseUpperLimitPeriod> limitSplitTolimitPeriod(DatePeriod period, List<ChildCareNurseUpperLimitSplit> upperLimitSplit){

		List<ChildCareNurseUpperLimitPeriod> childCareNurseUpperLimitPeriod = new ArrayList<>();

		// 上限日数分割日の件数ループ
		for(int idx = 0; idx < upperLimitSplit.size(); idx++) {
			val currentDayProcess = upperLimitSplit.get(idx);

			// 上限日数期間を上限日数分割日から作成
			// ===期間．開始日←「上限日数分割日．年月日」
			// ===期間．終了日←次の「上限日数分割日．年月日」の前日
			// ===　　　　※次の上限日数分割日がない場合、パラメータ「期間．終了日」
			// ===上限日数←上限日数分割日．上限日数
			if (idx == upperLimitSplit.size() - 1) {
				childCareNurseUpperLimitPeriod.add(ChildCareNurseUpperLimitPeriod.of(
						new DatePeriod(currentDayProcess.getYmd(), period.end()), currentDayProcess.getLimitDays()));
			} else {
				childCareNurseUpperLimitPeriod.add(ChildCareNurseUpperLimitPeriod.of(
						new DatePeriod(currentDayProcess.getYmd(), upperLimitSplit.get(idx + 1).getYmd().addDays(-1)),
						currentDayProcess.getLimitDays()));
			}
		}

		// 「上限日数期間（List）」を返す
		return childCareNurseUpperLimitPeriod;
	}

	/**
	 * require
	 *
	 */
	public static interface RequireM7 extends NursingLeaveSetting.RequireM7{

		// 介護看護休暇設定を取得する（会社ID、介護看護区分）
		NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory);
	}


	public static interface RequireM6 extends RequireM1, RequireM2, RequireM3{

		// 社員IDが一致する家族情報を取得（社員ID）
		List<FamilyInfo> familyInfo(String employeeId);

		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分）
		NursingCareLeaveRemainingInfo upperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory);

	}

	public static interface RequireM5 extends RequireM1{
		// 社員IDが一致する家族情報を取得（社員ID）
		List<FamilyInfo> familyInfo(String employeeId);
	}

	public static interface RequireM4 {
		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区）
		NursingCareLeaveRemainingInfo upperLimitPeriod(String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory);

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
	}

	public static interface Require extends RequireM1, RequireM2, RequireM3{
		// 社員IDが一致する家族情報を取得（社員ID）
		List<FamilyInfo> familyInfo(String employeeId);

		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分）
		NursingCareLeaveRemainingInfo upperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory);
	}

	public static interface RequireM1 {
		// 介護看護休暇設定を取得する（会社ID、介護看護区分）
		NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory);
	}

	public static interface RequireM2 {
		// 子の看護・介護休暇基本情報を取得する（社員ID）
		NursingCareLeaveRemainingInfo employeeInfo(String employeeId);
	}

	public static interface RequireM3 {
		// 会社の年休設定を取得する（会社ID）
		AnnualPaidLeaveSetting annualLeaveSet(String companyId);

		// 社員の契約時間を取得する（社員ID、基準日）
		LaborContractTime empContractTime(String employeeId, GeneralDate criteriaDate );

		// 年休の契約時間を取得する（会社ID、社員ID、基準日）
		LaborContractTime contractTime(String companyId, String employeeId,  GeneralDate criteriaDate);
	}
}