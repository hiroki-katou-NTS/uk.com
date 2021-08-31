package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.remainingnumber.common.dayandtime.DayAndTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimitPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;

/**
 * 子の看護介護休暇 集計期間WORK
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class AggregateChildCareNurseWork {
	/** 期間 */
	private DatePeriod period;
	/** 暫定子の看護介護管理データ */
	private List<TempChildCareNurseManagement> provisionalDate;
	/** 期間終了後翌日 */	private NextDayAfterPeriodEndWork nextDayAfterPeriodEnd;
	/** 本年翌年の期間区分 */
	private YearAtr yearAtr;
	/** 集計結果（finally） */
	private Finally<ChildCareNurseCalcResultWithinPeriod> aggrResultOfChildCareNurse;


	/**
	 * コンストラクタ
	 */
	public AggregateChildCareNurseWork(){

		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.provisionalDate = new ArrayList<>();
		this.nextDayAfterPeriodEnd = new NextDayAfterPeriodEndWork();
		this.yearAtr = YearAtr.THIS_YEAR;
		this.aggrResultOfChildCareNurse = Finally.empty();
	}

	/**
	 * ファクトリー
	 * @param period 期間
	 * @param provisionalDate 暫定子の看護介護管理データ
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param YearAtr 本年翌年の期間区分
	 * @param AggrResultOfChildCareNurse 集計結果（finally）
	 * @return 子の看護介護休暇 集計期間WORK
	 */
	public static AggregateChildCareNurseWork of(
		DatePeriod period,
		 List<TempChildCareNurseManagement> provisionalDate,
		NextDayAfterPeriodEndWork nextDayAfterPeriodEnd,
		YearAtr yearAtr,
		Finally<ChildCareNurseCalcResultWithinPeriod> aggrResultOfChildCareNurse){

	AggregateChildCareNurseWork domain = new AggregateChildCareNurseWork();
	domain.period = period;
	domain.provisionalDate = provisionalDate;
	domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
	domain.yearAtr = yearAtr;
	domain.aggrResultOfChildCareNurse = aggrResultOfChildCareNurse;
	return domain;
	}

	public boolean isThisYear() {
		return this.yearAtr == YearAtr.THIS_YEAR;
	}

	public boolean isNextYear() {
		return this.yearAtr == YearAtr.NEXT_YEAR;
	}

	/**
	 * エラーチェック
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param criteriaDate 基準日
	 * @param startUsed 月初時点の使用数
	 * Require｛
		介護看護休暇設定を取得する（会社ID、介護看護区分）
		子の看護・介護休暇基本情報を取得する（社員ID）
		年休の契約時間を取得する（社員ID、基準日）
		社員IDが一致する家族情報を取得（社員ID）
		介護対象管理データ（家族ID）
		期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分）
		｝
	 * @return 子の看護介護エラー情報 ChildCareNurseErrors
	 */
	public List<ChildCareNurseErrors> errorInfo(
			String companyId, String employeeId, DatePeriod period, GeneralDate criteriaDate,
			ChildCareNurseUsedNumber startUsed, NursingCategory nursingCategory, Require require) {

		List<ChildCareNurseErrors> errors = new ArrayList<>();

		// 集計期間の翌日を集計する時は、処理は行わない
		if(this.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()) {
			return errors;
		}

		// 暫定子の看護介護管理データを取得する
		ChildCareCheckOverUsedNumberWork checkOverUsedNumberWork = null;

		// 本年か翌年か確認
		if (isThisYear()) {
			// ＝＝＝＝＝＝本年　月初時点の使用数を超過確認用使用数に設定
			// ＝＝＝＝＝＝＝＝＝超過確認用使用数．日数　＝　INPUT．月初時点の使用数．使用日数
			// ＝＝＝＝＝＝＝＝＝超過確認用使用数．時間　＝　INPUT．月初時点の使用数．使用時間
			checkOverUsedNumberWork =
					ChildCareCheckOverUsedNumberWork.of(ChildCareNurseUsedNumber.of(startUsed.getUsedDay(), startUsed.getUsedTimes()));
		}else {
			// ＝＝＝＝＝＝翌年　超過確認用使用数を作成
			// ＝＝＝＝＝＝＝＝＝超過確認用使用数．日数　＝　0
			// ＝＝＝＝＝＝＝＝＝超過確認用使用数．時間　＝　0
			 checkOverUsedNumberWork =
					 ChildCareCheckOverUsedNumberWork.of(ChildCareNurseUsedNumber.of(new DayNumberOfUse(0d), Optional.of(new TimeOfUse(0))));
		}

		// 上限超過チェック
		// 暫定子の看護管理データの件数ループ
		for (int idx = 0; idx < provisionalDate.size(); idx++) {
			val currentDayProcess = provisionalDate.get(idx);

			errors.addAll(checkOverUsedNumberWork.checkOverUsedNumberWork(
					companyId, employeeId, period, criteriaDate, currentDayProcess, nursingCategory, require));
		}

		// 「子の看護介護エラー情報」を返す
		return errors;
	}

	/**
	 * 残数と使用数を計算する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param criteriaDate 基準日
	 * @param startUsed 月初時点の使用数
	 * @return  子の看護介護集計期間WORK
	 *
	 */
	public AggregateChildCareNurseWork calcRemainingUsed(String companyId, String employeeId, DatePeriod period,
			GeneralDate criteriaDate, ChildCareNurseUsedNumber startUsed, NursingCategory nursingCategory, Require require) {

		// 使用数計算
		ChildCareNurseCalcUsedNumber calcUsed = calcUsed(companyId, employeeId, period, criteriaDate, startUsed, require);

		// エラーチェック
		List<ChildCareNurseErrors> errorInfo = errorInfo(companyId, employeeId, period, criteriaDate, startUsed, nursingCategory, require);

		// 残数計算
		ChildCareNurseRemainingNumberCalcWork calcRemaining =
				calcRemaining(companyId, employeeId, period, criteriaDate, startUsed, calcUsed, nursingCategory, require);

		// 期間ごとの計算結果を作成
		ChildCareNurseCalcResultWithinPeriod calcResultWithinPeriod =
							ChildCareNurseCalcResultWithinPeriod.of(
									errorInfo,
									ChildCareNurseStartdateInfo.of(calcUsed.getStartdateInfo(),
																						calcRemaining.getRemainNumber(),
																						calcRemaining.getUpperLimit()),
									ChildCareNurseUsedInfo.of(calcUsed.getAggrPeriodUsedNumber(),
																					calcUsed.getUsedCount(),
																							calcUsed.getUsedDays()));

		// 期間ごとの計算結果を子の看護介護集計期間WORKを入れる
		// ===子の看護介護集計期間WORK．集計結果　＝　期間ごとの計算結果
		aggrResultOfChildCareNurse = Finally.of(calcResultWithinPeriod);

		// 「子の看護介護集計期間WORK」を返す
		return this;
	}

	/**
	 * 残数計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param criteriaDate 基準日
	 * @param startUsed 月初時点の使用数
	 * @param startDateUsed 起算日からの使用数
	 * @return  子の看護介護計算残数
	 */
	public ChildCareNurseRemainingNumberCalcWork calcRemaining(String companyId, String employeeId,
			DatePeriod period, GeneralDate criteriaDate, ChildCareNurseUsedNumber startUsed,
			ChildCareNurseCalcUsedNumber startDateUsed, NursingCategory nursingCategory, Require require) {

		// 集計期間の翌日を集計する時は、処理は行わない
		if(this.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()) {
			return new ChildCareNurseRemainingNumberCalcWork();
		}

		// INPUT．Require．子の看護・介護休暇基本情報を取得する
		Optional<NursingCareLeaveRemainingInfo> employeeInfo = require.employeeInfo(employeeId, nursingCategory);
		if(!employeeInfo.isPresent()) {
			return new ChildCareNurseRemainingNumberCalcWork();
		}

		// 期間ごとの上限日数を求める
		List<ChildCareNurseUpperLimitPeriod> childCareNurseUpperLimitPeriod = new ArrayList<>();
		childCareNurseUpperLimitPeriod = employeeInfo.get().childCareNurseUpperLimitPeriod(companyId, employeeId, period, criteriaDate, require);

		// 期間終了日時点の上限日数を確認
		// ===上限日数期間．期間．開始日 <= パラメータ「期間．終了日」<= 上限日数期間．期間．終了日
		ChildCareNurseUpperLimitPeriod upperLimitPeriod = childCareNurseUpperLimitPeriod.stream()
																									.filter(x -> x.getPeriod().contains(period.end())).findFirst().get();

		// 上限日数を設定
		// ===子の看護介護計算残数．上限日数←取得した「上限日数」
		ChildCareNurseRemainingNumberCalcWork upperLimit = new ChildCareNurseRemainingNumberCalcWork();
		upperLimit.setUpperLimit(upperLimitPeriod.getLimitDays());

		// 子の看護介護残数を求める
		ChildCareNurseRemainingNumber calcRemainingNumber = calcRemainingNumber(companyId,
				employeeId, upperLimitPeriod.getLimitDays(), startDateUsed.getStartdateInfo(), criteriaDate, require);

		// 残数を使い過ぎていないか
		boolean checkRemainingNumber = calcRemainingNumber.checkOverUpperLimit();

		if(checkRemainingNumber) {
			// 計算結果を残数に変換し起算日からの子の看護休暇情報．残数に設定
			// ===子の看護介護計算残数．残数　←受け取った「子の看護介護残数」
			upperLimit.setRemainNumber(calcRemainingNumber);
		}else {
			// 残数不足数を求める
			ChildCareNurseRemainingNumber calcShortageRemainingNumber =
					shortageRemainingNumber(companyId,employeeId, period, startUsed, criteriaDate, nursingCategory, require);
			upperLimit.setRemainNumber(calcShortageRemainingNumber);
		}
		// 「子の看護介護計算残数」を返す
		return upperLimit;
	}

	/**
	 * 子の看護介護残数を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param limitDays 上限日数
	 * @param usedNumber 使用数
	 * @param criteriaDate 基準日
	 * @return 残数が余っているか（true、false）
	 */
	public ChildCareNurseRemainingNumber calcRemainingNumber(String companyId, String employeeId,
			ChildCareNurseUpperLimit limitDays, ChildCareNurseUsedNumber usedNumber, GeneralDate criteriaDate, RequireM3 require) {

		// 日と時間の減算
		// ===減算される日と時間←日と時間
		// =====日数←上限日数、時間←0
		// ===減算する日と時間←日と時間
		// =====日数←使用数.日数、時間←使用数.時間
		DayAndTime subDayAndTime = DayAndTime.subDayAndTime(DayAndTime.of(new TimeOfUse(0), new DayNumberOfUse(limitDays.v().doubleValue())),
																						DayAndTime.of(usedNumber.getUsedTimes().orElse(new TimeOfUse(0)), usedNumber.getUsedDay()),
																						companyId,
																						employeeId,
																						criteriaDate,
																						require);

		// 日と時間を残数に変換する
		//	===	子の看護介護残数．日数　＝　日と時間．日
		//	===	子の看護介護残数．時間　＝　日と時間．時間
		DayNumberOfRemain remainDay = new DayNumberOfRemain(subDayAndTime.getDay().v());
		TimeOfRemain remainTimes = new TimeOfRemain(subDayAndTime.getTime().v());

		ChildCareNurseRemainingNumber remUsedNumber =ChildCareNurseRemainingNumber.of(remainDay,Optional.of(remainTimes));

		return remUsedNumber;
	}

	/**
	 * 残数不足数を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param startUsed 月初時点の使用数
	 * @param criteriaDate 基準日
	 * @param require
	 * 年休の契約時間を取得する（社員ID、基準日）
	 * 介護看護休暇設定を取得する（会社ID、介護看護区分）
	 * 社員IDが一致する家族情報を取得（社員ID）
	 * 介護対象管理データ（家族ID）
	 * @return 超過確認使用数、子の看護介護残数
	 */
	private ChildCareNurseRemainingNumber shortageRemainingNumber(String companyId,
			String employeeId, DatePeriod period, ChildCareNurseUsedNumber startUsed,
			GeneralDate criteriaDate, NursingCategory nursingCategory, RequireM4 require){

		ChildCareCheckOverUsedNumberWork checkOverUsedNumberWork;
		// 本年か翌年か
		if (isThisYear()) {
			// 期間開始時点の使用数を超過確認用使用数に設定
			// ===超過確認用使用数．使用数．日数　＝　INPUT．月初時点の使用数．使用日数
			// ===超過確認用使用数．使用数．時間　＝　INPUT．月初時点の使用数．使用時間
			checkOverUsedNumberWork =
					ChildCareCheckOverUsedNumberWork.of(ChildCareNurseUsedNumber.of(startUsed.getUsedDay(), startUsed.getUsedTimes()));
		}else {
			// 超過確認用使用数を作成
			// ===超過確認用使用数．使用数．日数　＝　0
			// ===超過確認用使用数．使用数．時間　＝　0
			checkOverUsedNumberWork =
					ChildCareCheckOverUsedNumberWork.of(ChildCareNurseUsedNumber.of(new DayNumberOfUse(0d), Optional.of(new TimeOfUse(0))));
		}

		// 子の看護介護残数を作成
		ChildCareNurseRemainingNumber remainingNumber = new ChildCareNurseRemainingNumber();

		// 暫定子の看護介護管理データの件数ループ
		for (int idx = 0; idx < provisionalDate.size(); idx++) {

			val currentDayProcess = provisionalDate.get(idx);

			// 残数不足数を計算
			//		===会社ID←パラメータ「会社ID」
			//		===社員ID←パラメータ「社員ID」
			//		===期間←パラメータ「期間」
			//		===基準日←パラメータ「基準日」
			//		===暫定子の看護介護管理データ←処理中の「暫定子の看護介護管理データ」
			//		===Require
			ChildCareShortageRemainingNumberWork shortageRemainingNumber =
					checkOverUsedNumberWork.calcShortageRemainingNumber(companyId, employeeId, period, criteriaDate, currentDayProcess, nursingCategory, require);

			// 計算結果を更新
			// ===超過確認用使用数 +=　子の看護介護残数不足数． 使用可能数
			// ===子の看護看護残数 ー=　子の看護介護残数不足数．残数不足数
			checkOverUsedNumberWork.getUsedNumber().add(shortageRemainingNumber.getAvailable());
			remainingNumber.sub(shortageRemainingNumber.getShortageRemNum());
		}

		// 「子の看護介護残数」を返す
		return remainingNumber;
	}

	/**
	 * 使用数計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 集計期間
	 * @param criteriaDate 基準日
	 * @param startUsed 月初時点の使用数
	 * @return 子の看護介護計算使用数
	 *
	 */
	public ChildCareNurseCalcUsedNumber calcUsed(String companyId, String employeeId, DatePeriod period, GeneralDate criteriaDate, ChildCareNurseUsedNumber startUsed, Require require) {

		// 集計期間の翌日を集計する時は、処理は行わない
		if(this.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()) {
			return new ChildCareNurseCalcUsedNumber();
		}
		// 暫定子の看護管理データの合計を求める
		//	===	使用合計．使用日数←暫定子の看護介護管理データ．使用日数を全て加算
		//	===	使用合計．使用時間←暫定子の看護介護管理データ．使用時間を全て加算
		int time = 0;
		double days = 0d;
		for (int idx = 0; idx < provisionalDate.size(); idx++) {
			val currentDayProcess = provisionalDate.get(idx);

			days += currentDayProcess.getUsedNumber().getUsedDay().v();
			time += currentDayProcess.getUsedNumber().getUsedTimes().map(x -> x.v()).orElse(0);
		}
		ChildCareNurseUsedNumber sumUsedNum = ChildCareNurseUsedNumber.of(new DayNumberOfUse(days), Optional.of(new TimeOfUse(time)));

		// 子の看護介護計算使用数に合計を設定
		ChildCareNurseCalcUsedNumber childCareNurseCalcUsedNumber = new ChildCareNurseCalcUsedNumber();
		childCareNurseCalcUsedNumber.setAggrPeriodUsedNumber(sumUsedNum);

		// 起算日からの使用数に加算
		childCareNurseCalcUsedNumber = startMonthDayInfo(companyId, employeeId, criteriaDate, childCareNurseCalcUsedNumber, startUsed, require);

		// 時間休暇使用回数を求める
		int usedTimeCount = provisionalDate.stream().filter(c -> c.getUsedNumber().getUsedTimes().isPresent()).mapToInt(c -> 1).sum();
		childCareNurseCalcUsedNumber.setUsedCount(new UsedTimes(usedTimeCount));

		// 時間休暇使用日数を求める
		Map<GeneralDate, List<TempChildCareNurseManagement>> usedTimeByDay = provisionalDate.stream().filter(c -> c.getUsedNumber().getUsedTimes().isPresent())
																																								.collect(Collectors.groupingBy(c -> c.getYmd(), Collectors.toList()));
		int usedDayCount = usedTimeByDay.size();
		childCareNurseCalcUsedNumber.setUsedDays(new UsedTimes(usedDayCount));

		// 「子の看護介護計算使用数」を返す
		return childCareNurseCalcUsedNumber;
	}

	/**
	 * 起算日からの使用数に加算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param calcUsed 子の看護介護計算使用数
	 * @param startUsed 月初時点の使用数
	 * @return  子の看護介護計算使用数
	 */
	public ChildCareNurseCalcUsedNumber startMonthDayInfo(String companyId, String employeeId, GeneralDate criteriaDate,
			ChildCareNurseCalcUsedNumber calcUsed, ChildCareNurseUsedNumber startUsed, RequireM3 require) {

		// 処理期間が本年か翌年か
		if(isThisYear()) {
			// 月初時点の使用数を起算日からの使用数に加算
			// ===子の看護介護計算使用数．起算日からの使用数．日数←INPUT．月初時点の使用数．使用日数
			// ===子の看護介護計算使用数．起算日からの使用数．時間←INPUT．月初時点の使用数．使用時間
			calcUsed.getStartdateInfo().add(ChildCareNurseUsedNumber.of(startUsed.getUsedDay(), startUsed.getUsedTimes()));
		}else {
			// 起算日からの使用数を作成
			// ===子の看護介護計算使用数．起算日からの使用数 ．日数=0、時間＝0で作成
			calcUsed.setStartdateInfo(ChildCareNurseUsedNumber.of(new DayNumberOfUse(0d), Optional.of(new TimeOfUse(0))));
		}

		// 期間の使用数に集計期間の使用数を加算
		// ===起算日からの使用数．日数　＋＝　パラメータ「子の看護介護計算使用数．集計期間の使用数．日数」
		// ===起算日からの使用数．時間　＋＝　パラメータ「子の看護介護計算使用数．集計期間の使用数．使用時間」
		calcUsed.getStartdateInfo().add(calcUsed.getAggrPeriodUsedNumber());

		// 時間使用数を日数に積み上げ
		ChildCareNurseUsedNumber usedDayfromUsedTime = calcUsed.getStartdateInfo().contractTime(require, companyId, employeeId, criteriaDate);
		calcUsed.setStartdateInfo(usedDayfromUsedTime);

		// 「子の看護介護計算使用数」を返す
		return calcUsed;
	}

	/**
	 * require
	 *
	 */
	public static interface RequireM6 extends RequireM1, RequireM2, RequireM3{
		// 社員IDが一致する家族情報を取得（社員ID）
		List<FamilyInfo> familyInfo(String employeeId);

		// 介護対象管理データ（家族ID）
		CareManagementDate careData(String familyID);

		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分）
		NursingCareLeaveRemainingInfo upperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory);
	}

	public static interface RequireM5 extends RequireM1{
		// 社員IDが一致する家族情報を取得（社員ID）
		List<FamilyInfo> familyInfo(String employeeId);

		// 介護対象管理データ（家族ID）
		Optional<CareManagementDate> careData(String familyID);
	}

	public static interface RequireM4 extends RequireM1, RequireM3, RequireM5,ChildCareCheckOverUsedNumberWork.RequireM4 {
		// 年休と介護設定等　計６つ
	}

	public static interface Require extends RequireM1, RequireM2, RequireM3 , RequireM4, ChildCareCheckOverUsedNumberWork.Require, ChildCareCheckOverUsedNumberWork.RequireM4, NursingCareLeaveRemainingInfo.RequireM7{

	}

	public static interface RequireM1 {
		// 介護看護休暇設定を取得する（会社ID、介護看護区分）
		NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory);
	}

	public static interface RequireM2 {
		// 子の看護・介護休暇基本情報を取得する（社員ID）
		Optional<NursingCareLeaveRemainingInfo> employeeInfo(String employeeId, NursingCategory nursingCategory);
	}

	public static interface RequireM3 extends DayAndTime.RequireM3, ChildCareCheckOverUsedNumberWork.RequireM3, ChildCareNurseUsedNumber.RequireM3 {
		// 年休
	}
}