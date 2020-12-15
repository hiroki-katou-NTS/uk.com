package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveRemainNoMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.ActualSpecialLeaveRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.ActualSpecialLeaveRemainDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeavaRemainTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveGrantUseDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumberInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUnDigestion;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUsedInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 特休の集計結果
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class InPeriodOfSpecialLeaveResultInfor {

	/** 特休情報（期間終了日時点） */
	private SpecialLeaveInfo asOfPeriodEnd;
	/** 特休情報（期間終了日の翌日開始時点） */
	private SpecialLeaveInfo asOfStartNextDayOfPeriodEnd;
	/** 特休情報（付与時点） */
	private Optional<List<SpecialLeaveInfo>> asOfGrant;
	/** 特休情報（消滅） */
	private Optional<List<SpecialLeaveInfo>> lapsed;
	/** 特休エラー情報 */
	private List<SpecialLeaveError> specialLeaveErrors;

	/**
	 * コンストラクタ
	 */
	public InPeriodOfSpecialLeaveResultInfor(){
		this.asOfPeriodEnd = new SpecialLeaveInfo();
		this.asOfStartNextDayOfPeriodEnd = new SpecialLeaveInfo();
		this.asOfGrant = Optional.empty();
		this.lapsed = Optional.empty();
		this.specialLeaveErrors = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param asOfPeriodEnd 特休情報（期間終了日時点）
	 * @param asOfStartNextDayOfPeriodEnd 特休情報（期間終了日の翌日開始時点）
	 * @param asOfGrant 特休情報（付与時点）
	 * @param lapsed 特休情報（消滅）
	 * @param specialLeaveErrors 特休エラー情報
	 * @return 特休の集計結果
	 */
	public static InPeriodOfSpecialLeaveResultInfor of(
			SpecialLeaveInfo asOfPeriodEnd,
			SpecialLeaveInfo asOfStartNextDayOfPeriodEnd,
			Optional<List<SpecialLeaveInfo>> asOfGrant,
			Optional<List<SpecialLeaveInfo>> lapsed,
			List<SpecialLeaveError> specialLeaveErrors){

		InPeriodOfSpecialLeaveResultInfor domain = new InPeriodOfSpecialLeaveResultInfor();
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.asOfStartNextDayOfPeriodEnd = asOfStartNextDayOfPeriodEnd;
		domain.asOfGrant = asOfGrant;
		domain.lapsed = lapsed;
		domain.specialLeaveErrors = specialLeaveErrors;
		return domain;
	}

	/**
	 * 特休エラー情報の追加
	 * @param error 特休エラー情報
	 */
	public void addError(SpecialLeaveError error){

		if (this.specialLeaveErrors.contains(error)) return;
		this.specialLeaveErrors.add(error);
	}

	public List<nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError> getErrorlistSharedClass(){
		List<nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError> result = new ArrayList<>();

		specialLeaveErrors.stream().forEach(c->
			result.add(EnumAdaptor.valueOf(c.value, nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError.class)));

		return result;
	}

	/*
	 * 特別休暇月別残数データを作成
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param period 期間
	 * @param specialLeaveCode 特別休暇コード
	 * @param remainNoMinus 特別休暇の残数マイナスなし
	 * @return 特別休暇月別残数データ
	 */
	public SpecialHolidayRemainData createSpecialHolidayRemainData(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod period,
			int specialLeaveCode,
			SpecialLeaveRemainNoMinus remainNoMinus){

//		【項目移送】

		SpecialHolidayRemainData domain = new SpecialHolidayRemainData();

//		社員ID　←　パラメータ「社員ID」
		domain.setSid(employeeId);

//		年月　　←　パラメータ「年月」
		domain.setYm(yearMonth);

//		締めID　←　パラメータ「締めID」
		domain.setClosureId(closureId.value);

//		締め期間　←　パラメータ「期間」
		domain.setClosurePeriod(period);

//		締め処理状態　←　”未締め”
		domain.setClosureStatus(ClosureStatus.UNTREATED);		// 未締め

//		締め日　←　パラメータ「締め日」
		domain.setClosureDate(closureDate);

//		特別休暇コード　←　パラメータ「特別休暇コード」
		domain.setSpecialHolidayCd(specialLeaveCode);

		// 特別休暇：使用数
		double specialUseDays = remainNoMinus.getUseDaysBeforeGrant();

//		特別休暇．使用数．使用日数．使用日数付与前　←　特別休暇の残数マイナスなし．使用数付与前
		SpecialLeaveRemainDay specialUseDaysBefore = new SpecialLeaveRemainDay(
				remainNoMinus.getUseDaysBeforeGrant());

//		特別休暇．使用数．使用日数．使用日数付与後　←　特別休暇の残数マイナスなし．使用数付与後
		Optional<SpecialLeaveUseNumber> specialLeaveUseNumberAfterOpt = Optional.empty();
		if (remainNoMinus.getUseDaysAfterGrant().isPresent()){
			SpecialLeaveRemainDay specialUseDaysAfter = new SpecialLeaveRemainDay(remainNoMinus.getUseDaysAfterGrant().get());
			specialLeaveUseNumberAfterOpt
				= Optional.of(SpecialLeaveUseNumber.of(SpecialLeaveUseDays.of(specialUseDaysAfter),Optional.empty()));

			specialUseDays += specialUseDaysAfter.v();
		}

//		特別休暇．使用数．使用日数．使用日数　←　特別休暇．使用数．使用日数．使用日数付与前
//　　　　　　　　＋　特別休暇．使用数．使用日数．使用日数付与後
		SpecialLeaveUseDays specialUseNumberDays = new SpecialLeaveUseDays(
				new SpecialLeaveRemainDay(specialUseDays));

		// 特別休暇 使用数
		SpecialLeaveUsedInfo specialLeaveUsedInfo
			= SpecialLeaveUsedInfo.of(
					SpecialLeaveUseNumber.of(specialUseNumberDays, Optional.empty()),
					SpecialLeaveUseNumber.of(SpecialLeaveUseDays.of(specialUseDaysBefore),Optional.empty()),
					new UsedTimes(0),
					new UsedTimes(0),
					specialLeaveUseNumberAfterOpt
					);

//		特別休暇．未消化数　←　特別休暇情報（期間終了日の翌日開始時点）．残数．未消化数
		// 特別休暇：未消化数
//		inPeriod.getAsOfStartNextDayOfPeriodEnd()
		if (this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveUndigestNumber().isPresent()){
			/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休未消化数 */
			Double days = this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveUndigestNumber().get().getDays().v();
			SpecialLeaveRemainDay remainDay = new SpecialLeaveRemainDay(days);
			SpecialLeaveUnDigestion unDegestionNumber
				= new SpecialLeaveUnDigestion(remainDay, Optional.empty());
		}

		// 特別休暇：残数

//		特別休暇．残数付与前．日数　←　特別休暇の残数マイナスなし．残数付与前
		SpecialLeaveRemainingNumber specialRemainBefore
			= SpecialLeaveRemainingNumber.createFromJavaType(
				remainNoMinus.getRemainDaysBeforeGrant(), 0);

//		特別休暇．残数付与後．日数　←　特別休暇の残数マイナスなし．残数付与後
		Optional<SpecialLeaveRemainingNumber> specialRemainAfterOpt = Optional.empty();
		if (remainNoMinus.getRemainDaysAfterGrant().isPresent()){
			specialRemainAfterOpt = Optional.of(SpecialLeaveRemainingNumber.createFromJavaType(
				remainNoMinus.getRemainDaysAfterGrant().get().doubleValue(), 0) );
		}

//		特別休暇．残数．日数　←　（特別休暇の残数マイナスなし．残数付与後が存在する場合）
//　　　　　　　　特別休暇の残数マイナスなし．残数付与後
//　　　　　　　　（存在しない場合）
//　　　　　　　　特別休暇の残数マイナスなし．残数付与前
		SpecialLeaveRemainingNumber specialRemain = null;
		if (specialRemainAfterOpt.isPresent()){
			specialRemain = specialRemainAfterOpt.get();
		}

		SpecialLeaveRemainingNumberInfo specialLeaveRemainingNumberInfo
			= new SpecialLeaveRemainingNumberInfo(
					specialRemain, specialRemainBefore, specialRemainAfterOpt);

		// 特別休暇
		domain.setSpecialLeave( new SpecialLeave(
				specialLeaveUsedInfo, specialLeaveRemainingNumberInfo) );

		/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスなし）.特別休暇使用情報.付与前.使用日数.使用日数 */
		double actualUseDaysBefore
			= this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().getUseDays().v();
		double actualSpecialUseDays = actualUseDaysBefore;

//		実特別休暇．使用数．使用日数．使用日数付与後　←　特別休暇の残数．付与後明細．使用数
		Optional<SpecialLeaveUseNumber> actualSpecialLeaveUseNumberAfterOpt = Optional.empty();

		if (this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()){
			/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスあり）.特別休暇使用情報.付与後.使用日数.使用日数 */
			double actualUseDaysAfter = this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().getUseDays().v();
//			if (this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()){
//				/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスあり）.特別休暇使用情報.付与後.使用時間.使用時間 */
//				this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes();
//			}
			actualSpecialLeaveUseNumberAfterOpt
					= Optional.of(SpecialLeaveUseNumber.of(
						SpecialLeaveUseDays.of(new SpecialLeaveRemainDay(actualSpecialUseDays)), Optional.empty()));
			actualSpecialUseDays += actualUseDaysAfter;
		}

//		実特別休暇．使用数．使用日数．使用日数　←　実特別休暇．使用数．使用日数．使用日数付与前
//　　　　　　	＋　実特別休暇．使用数．使用日数．使用日数付与後
		SpecialLeaveUseDays actualSpecialUseNumberDays = new SpecialLeaveUseDays(
				new SpecialLeaveRemainDay(actualSpecialUseDays));

		// 特別休暇：残数

////		実特別休暇．残数付与前．日数　←　特別休暇の残数．付与前明細．残数
//		SpecialLeaveRemain actualSpecialRemainBefore = new SpecialLeaveRemain(
//				new SpecialLeaveRemainDay(inPeriod.getRemainDays().getGrantDetailBefore().getRemainDays()),
//				Optional.empty());

		double days = 0.0;
		int time = 0;

		if (this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()){
			/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスあり）.特別休暇残数情報.付与後.合計残日数 */
			days = this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().
					getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v();

			if (this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent()){
				/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスあり）.特別休暇残数情報.付与後.合計残時間 */
				time = this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v();
			}
		}
		specialRemainAfterOpt = Optional.of(SpecialLeaveRemainingNumber.createFromJavaType(days, time));

		// 特別休暇

		/** 特休の集計結果.特休情報（期間終了日の翌日開始時点）.残数.特休（マイナスなし）.特別休暇使用情報.特休使用回数 （1日2回使用した場合２回でカウント） */
		int usedTimes = this.getAsOfStartNextDayOfPeriodEnd().getRemainingNumber().getSpecialLeaveNoMinus().getUsedNumberInfo().getSpecialLeaveUsedTimes().v();
		/** 特休の集計結果.特休情報（期間終了日の翌日開始時点）.残数.特休（マイナスなし）.特別休暇使用情報.特休使用日数 （1日2回使用した場合１回でカウント） */
		int usedDayTimes = this.getAsOfStartNextDayOfPeriodEnd().getRemainingNumber().getSpecialLeaveNoMinus().getUsedNumberInfo().getSpecialLeaveUsedDayTimes().v();

		// 特別休暇 使用数
		SpecialLeaveUsedInfo actualSpecialLeaveUsedInfo
			= SpecialLeaveUsedInfo.of(
					SpecialLeaveUseNumber.of(specialUseNumberDays, Optional.empty()),
					SpecialLeaveUseNumber.of(SpecialLeaveUseDays.of(specialUseDaysBefore),Optional.empty()),
					new UsedTimes(usedTimes),
					new UsedTimes(usedDayTimes),
					specialLeaveUseNumberAfterOpt
					);

		// 特別休暇 残数

		// 合計
		SpecialLeaveRemainingNumber actualSpecialLeaveRemainingNumber
			= new SpecialLeaveRemainingNumber();
		// 付与前
		SpecialLeaveRemainingNumber actualRemainingNumberBeforeGrant
			= new SpecialLeaveRemainingNumber();
		// 付与後
		Optional<SpecialLeaveRemainingNumber> actualRemainingNumberAfterGrantOpt
			= Optional.empty();

//		実特別休暇．残数　←　（特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在する場合）
//		　　　　　　　　　　　　　　　　　　特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスなし）残数．付与後
//		　　　　　　　　　　　　　　　　　　（存在しない場合）
//		　　　　　　　　　　　　　　　　　　特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスなし）残数．付与前

		// 特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在する場合
		if (this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()){
			/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスあり）.特別休暇残数情報.付与後.合計残日数 */
			actualRemainingNumberBeforeGrant.setDayNumberOfRemain(
					new DayNumberOfRemain(this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus()
							.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v()));
//			if (this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent()){
//				/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスあり）.特別休暇残数情報.付与後.合計残時間 */
//				this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get();
//			}
		} else { // （存在しない場合）
			/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスあり）.特別休暇残数情報.付与前.合計残日数 */
			actualRemainingNumberBeforeGrant.setDayNumberOfRemain(
					new DayNumberOfRemain(this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus()
							.getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v()));
//			if (c.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent()){
//				/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスあり）.特別休暇残数情報.付与前.合計残時間 */
//				c.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get();
//			}
		}

		// 特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在する場合
		if (this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveNoMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()){
			/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスなし）.特別休暇残数情報.付与後.合計残日数 */
			actualSpecialLeaveRemainingNumber.setDayNumberOfRemain(
					new DayNumberOfRemain(
							this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveNoMinus().getRemainingNumberInfo()
							.getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v()));

			SpecialLeaveRemainingNumber remainingNumberAfterGrant = new SpecialLeaveRemainingNumber();
			remainingNumberAfterGrant.setDayNumberOfRemain(
				new DayNumberOfRemain(specialRemainAfterOpt.get().getDayNumberOfRemain().v()));
			Optional<SpecialLeaveRemainingNumber> remainingNumberAfterGrantOpt
				= Optional.of(remainingNumberAfterGrant);
		}
		else { // 存在しない場合
//	　　　　　　  特別休暇の残数マイナスなし．残数付与前
			actualSpecialLeaveRemainingNumber.setDayNumberOfRemain(
					new DayNumberOfRemain(specialRemainBefore.getDayNumberOfRemain().v()));
		}

		/** 特休の集計結果.特休情報（期間終了日時点）.残数.特休（マイナスなし）.特別休暇残数情報.合計.合計残日数 */
		val remainDays = this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveNoMinus().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain();

		// 要修正　要見直し
		// 実特別休暇：残数
		ActualSpecialLeaveRemain actualRemainBefore = new ActualSpecialLeaveRemain(
				new ActualSpecialLeaveRemainDay(remainDays.v()),
				Optional.empty());
		ActualSpecialLeaveRemain actualRemainAfter = null;

//		if (remainDays.isPresent()){
//			actualRemainAfter = new ActualSpecialLeaveRemain(
//					new ActualSpecialLeaveRemainDay(remainDays.v()),
//					Optional.empty());
//		}

//		// 実特別休暇：残数
//		ActualSpecialLeaveRemain actualRemainBefore = new ActualSpecialLeaveRemain(
//				new ActualSpecialLeaveRemainDay(remainDays.getGrantDetailBefore().getRemainDays()),
//				Optional.empty());
//		ActualSpecialLeaveRemain actualRemainAfter = null;
//		if (remainDays.getGrantDetailAfter().isPresent()){
//			actualRemainAfter = new ActualSpecialLeaveRemain(
//					new ActualSpecialLeaveRemainDay(remainDays.getGrantDetailAfter().get().getRemainDays()),
//					Optional.empty());
//		}

		// 実特別休暇
		domain.setActualSpecial(
				SpecialLeave.of(
						specialLeaveUsedInfo,
						specialLeaveRemainingNumberInfo));

		SpecialLeaveUsedInfo usedNumberInfoTmp = new SpecialLeaveUsedInfo();
		SpecialLeaveRemainingNumberInfo remainingNumberInfo = new SpecialLeaveRemainingNumberInfo();

		// 付与区分
		domain.setGrantAtr(this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus()
				.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent());

		// 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if ( this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus()
				.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()) {
			grantDays = new SpecialLeaveGrantUseDay(
				this.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus()
				.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v());
		}

		domain.setGrantDays(Optional.ofNullable(grantDays));

		return domain;
	}

}
