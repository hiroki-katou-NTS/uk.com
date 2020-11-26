package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveRemainNoMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumber;

/**
 * 特別休暇月別残数データ
 *
 * @author do_dt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SpecialHolidayRemainData extends AggregateRoot {
	/**
	 * 社員ID
	 */
	private String sid;
	/**
	 * 年月
	 */
	private YearMonth ym;
	/**
	 * 締めID
	 */
	private int closureId;

	/**
	 * 締め期間
	 */
	private DatePeriod closurePeriod;
	/**
	 * 締め処理状態
	 */
	private ClosureStatus closureStatus;
	/**
	 * 締め日付
	 */
	private ClosureDate closureDate;
	/**
	 * 特別休暇コード
	 */
	private int specialHolidayCd;
	/**
	 * 実特別休暇
	 */
	private SpecialLeave actualSpecial;
	/**
	 * 特別休暇
	 */
	private SpecialLeave specialLeave;
	/**
	 * 付与区分
	 */
	private boolean grantAtr;
	/**
	 * 未消化数
	 */
	private SpecialLeaveUnDigestion unDegestionNumber;
	/**
	 * 特別休暇付与情報: 付与日数
	 */
	private Optional<SpecialLeaveGrantUseDay> grantDays;

	/*
	 * 特別休暇月別残数データを作成
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param period 期間
	 * @param specialLeaveCode 特別休暇コード
	 * @param inPeriod 特別休暇の集計結果
	 * @param remainNoMinus 特別休暇の残数マイナスなし
	 * @return 特別休暇月別残数データ
	 */
	public static SpecialHolidayRemainData of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod period,
			int specialLeaveCode,
//			InPeriodOfSpecialLeaveResultInfor inPeriod,
			SpecialLeaveRemainNoMinus remainNoMinus){

		// ooooo 要修正 jinno　一時的にコメントアウト
//
////		【項目移送】
//
//		SpecialHolidayRemainData domain = new SpecialHolidayRemainData();
//
////		社員ID　←　パラメータ「社員ID」
//		domain.sid = employeeId;
//
////		年月　　←　パラメータ「年月」
//		domain.ym = yearMonth;
//
////		締めID　←　パラメータ「締めID」
//		domain.closureId = closureId.value;
//
////		締め期間　←　パラメータ「期間」
//		domain.closurePeriod = period;
//
////		締め処理状態　←　”未締め”
//		domain.closureStatus = ClosureStatus.UNTREATED;		// 未締め
//
////		締め日　←　パラメータ「締め日」
//		domain.closureDate = closureDate;
//
////		特別休暇コード　←　パラメータ「特別休暇コード」
//		domain.specialHolidayCd = specialLeaveCode;
//
//
//		// 特別休暇：使用数
//		double specialUseDays = remainNoMinus.getUseDaysBeforeGrant();
//
////		特別休暇．使用数．使用日数．使用日数付与前　←　特別休暇の残数マイナスなし．使用数付与前
//		SpecialLeaveRemainDay specialUseDaysBefore = new SpecialLeaveRemainDay(
//				remainNoMinus.getUseDaysBeforeGrant());
//
////		特別休暇．使用数．使用日数．使用日数付与後　←　特別休暇の残数マイナスなし．使用数付与後
//		Optional<SpecialLeaveUseNumber> specialLeaveUseNumberAfterOpt = Optional.empty();
//		if (remainNoMinus.getUseDaysAfterGrant().isPresent()){
//			SpecialLeaveRemainDay specialUseDaysAfter = new SpecialLeaveRemainDay(remainNoMinus.getUseDaysAfterGrant().get());
//			specialLeaveUseNumberAfterOpt
//				= Optional.of(SpecialLeaveUseNumber.of(SpecialLeaveUseDays.of(specialUseDaysAfter),Optional.empty()));
//
//			specialUseDays += specialUseDaysAfter.v();
//		}
//
////		特別休暇．使用数．使用日数．使用日数　←　特別休暇．使用数．使用日数．使用日数付与前
////　　　　　　　　＋　特別休暇．使用数．使用日数．使用日数付与後
//		SpecialLeaveUseDays specialUseNumberDays = new SpecialLeaveUseDays(
//				new SpecialLeaveRemainDay(specialUseDays));
//
//		// 特別休暇 使用数
//		SpecialLeaveUsedInfo specialLeaveUsedInfo
//			= SpecialLeaveUsedInfo.of(
//					SpecialLeaveUseNumber.of(specialUseNumberDays, Optional.empty()),
//					SpecialLeaveUseNumber.of(SpecialLeaveUseDays.of(specialUseDaysBefore),Optional.empty()),
//					new UsedTimes(0), // ooooo要修正！！
//					new UsedTimes(0), // ooooo要修正！！
//					specialLeaveUseNumberAfterOpt
//					);
//
//
////		特別休暇．未消化数　←　特別休暇情報（期間終了日の翌日開始時点）．残数．未消化数
//		// ooooo要修正！！
//		// 特別休暇：未消化数
////		inPeriod.getAsOfStartNextDayOfPeriodEnd()
//		SpecialLeaveRemainDay a = new SpecialLeaveRemainDay(
//				inPeriod.getRemainDays().getUnDisgesteDays());
//		SpecialLeaveUnDigestion unDegestionNumber
//			= new SpecialLeaveUnDigestion(a, Optional.empty());
//
//
//		// 特別休暇：残数
//
////		特別休暇．残数付与前．日数　←　特別休暇の残数マイナスなし．残数付与前
//		SpecialLeaveRemainingNumber specialRemainBefore
//			= SpecialLeaveRemainingNumber.createFromJavaType(
//				remainNoMinus.getRemainDaysBeforeGrant(), 0);
//
////		特別休暇．残数付与後．日数　←　特別休暇の残数マイナスなし．残数付与後
//		Optional<SpecialLeaveRemainingNumber> specialRemainAfterOpt = Optional.empty();
//		if (remainNoMinus.getRemainDaysAfterGrant().isPresent()){
//			specialRemainAfterOpt = Optional.of(SpecialLeaveRemainingNumber.createFromJavaType(
//					remainNoMinus.getRemainDaysAfterGrant().get().doubleValue(), 0) );
//		}
//
////		特別休暇．残数．日数　←　（特別休暇の残数マイナスなし．残数付与後が存在する場合）
////　　　　　　　　特別休暇の残数マイナスなし．残数付与後
////　　　　　　　　（存在しない場合）
////　　　　　　　　特別休暇の残数マイナスなし．残数付与前
//		SpecialLeaveRemainingNumber specialRemain;
//		if (specialRemainAfterOpt.isPresent()){
//			specialRemain = specialRemainAfterOpt.get();
//		}
//
//		SpecialLeaveRemainingNumberInfo specialLeaveRemainingNumberInfo
//			= new SpecialLeaveRemainingNumberInfo(
//					specialRemain, specialRemainBefore, specialRemainAfterOpt);
//
//		// 特別休暇
//		domain.specialLeave = new SpecialLeave(
//				specialLeaveUsedInfo, specialLeaveRemainingNumberInfo);
//
//
//
//
//
//
//
//		// 実特別休暇：使用数
////		実特別休暇．使用数．使用日数．使用日数付与前　←　特別休暇の残数．付与前明細．使用数
////		double actualUseDaysBefore = inPeriod.getRemainDays().getGrantDetailBefore().getUseDays();
//		double actualSpecialUseDays = actualUseDaysBefore;
//
////		実特別休暇．使用数．使用日数．使用日数付与後　←　特別休暇の残数．付与後明細．使用数
//		Optional<SpecialLeaveUseNumber> actualSpecialLeaveUseNumberAfterOpt = Optional.empty();
//
//		if (inPeriod.getRemainDays().getGrantDetailAfter().isPresent()){
//			double actualUseDaysAfter = inPeriod.getRemainDays().getGrantDetailAfter().get().getUseDays();
//			SpecialLeaveRemainDay actualSpecialUseDaysAfter
//				= new SpecialLeaveRemainDay(actualUseDaysAfter);
//
//			actualSpecialLeaveUseNumberAfterOpt
//				= Optional.of(SpecialLeaveUseNumber.of(
//						SpecialLeaveUseDays.of(actualSpecialUseDaysAfter),Optional.empty()));
//
//			actualSpecialUseDays += actualSpecialUseDaysAfter.v();
//		}
//
////		実特別休暇．使用数．使用日数．使用日数　←　実特別休暇．使用数．使用日数．使用日数付与前
////　　　　　　	＋　実特別休暇．使用数．使用日数．使用日数付与後
//		SpecialLeaveUseDays actualSpecialUseNumberDays = new SpecialLeaveUseDays(
//				new SpecialLeaveRemainDay(actualSpecialUseDays));
//
//
//		// 特別休暇：残数
//
////		実特別休暇．残数付与前．日数　←　特別休暇の残数．付与前明細．残数
//		SpecialLeaveRemain actualSpecialRemainBefore = new SpecialLeaveRemain(
//				new SpecialLeaveRemainDay(inPeriod.getRemainDays().getGrantDetailBefore().getRemainDays()),
//				Optional.empty());
//
////		実特別休暇．残数付与後．日数　←　特別休暇の残数．付与後明細．残数
//		Optional<SpecialLeaveRemain> actualSpecialRemainAfterOpt = Optional.empty();
//		if (inPeriod.getRemainDays().getGrantDetailAfter().isPresent()){
//			specialRemainAfterOpt = Optional.of(new SpecialLeaveRemain(
//					new SpecialLeaveRemainDay(inPeriod.getRemainDays().getGrantDetailAfter().get().getRemainDays()),
//					Optional.empty()));
//		}
//
//		// 特別休暇
//
//		// 特別休暇 使用数
//		SpecialLeaveUsedInfo actualSpecialLeaveUsedInfo
//			= SpecialLeaveUsedInfo.of(
//					SpecialLeaveUseNumber.of(specialUseNumberDays, Optional.empty()),
//					SpecialLeaveUseNumber.of(SpecialLeaveUseDays.of(specialUseDaysBefore),Optional.empty()),
//					new UsedTimes(0), // ooooo要修正！！
//					new UsedTimes(0), // ooooo要修正！！
//					specialLeaveUseNumberAfterOpt
//					);
//
//		// 特別休暇 残数
//
//		// 合計
//		SpecialLeaveRemainingNumber actualSpecialLeaveRemainingNumber
//			= new SpecialLeaveRemainingNumber();
//		// 付与前
//		SpecialLeaveRemainingNumber actualRemainingNumberBeforeGrant
//			= new SpecialLeaveRemainingNumber();
//		// 付与後
//		Optional<SpecialLeaveRemainingNumber> actualRemainingNumberAfterGrantOpt
//			= Optional.empty();
//
//
////		実特別休暇．残数　←　（特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在する場合）
////		　　　　　　　　　　　　　　　　　　特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスなし）残数．付与後
////		　　　　　　　　　　　　　　　　　　（存在しない場合）
////		　　　　　　　　　　　　　　　　　　特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスなし）残数．付与前
//
//		actualRemainingNumberBeforeGrant.setDayNumberOfRemain( // ooooo要修正！！確認
//				new DayNumberOfRemain(actualSpecialRemainBefore.getDays().v()));
//
//		// 特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在する場合
//		if ( inPeriod.getRemainDays().getGrantDetailAfter().isPresent() ){
////	　　　　　　特別休暇情報（期間終了日時点）．残数．特別休暇（マイナスなし）残数．付与後
//			actualSpecialLeaveRemainingNumber.setDayNumberOfRemain(
//				new DayNumberOfRemain(actualSpecialRemainAfterOpt.get().getDays().v()));
//
//			SpecialLeaveRemainingNumber remainingNumberAfterGrant
//				= new SpecialLeaveRemainingNumber();
//			remainingNumberAfterGrant.setDayNumberOfRemain(
//					new DayNumberOfRemain(specialRemainAfterOpt.get().getDayNumberOfRemain().v()));
//			Optional<SpecialLeaveRemainingNumber> remainingNumberAfterGrantOpt
//				= Optional.of(remainingNumberAfterGrant);
//		}
//		else { // 存在しない場合
////	　　　　　　特別休暇の残数マイナスなし．残数付与前
//			specialLeaveRemainingNumber.setDayNumberOfRemain(
//					new DayNumberOfRemain(specialRemainBefore.getDayNumberOfRemain().v()));
//		}
//
////		SpecialLeaveRemainingNumberInfo specialLeaveRemainingNumberInfo
////			= SpecialLeaveRemainingNumberInfo.of(
////					specialLeaveRemainingNumber, remainingNumberBeforeGrant, remainingNumberAfterGrantOpt);
////
////		// 特別休暇
////		domain.specialLeave = new SpecialLeave(
////				specialLeaveUsedInfo, specialLeaveRemainingNumberInfo);
//
//// ------------------------------------------------
//
//		val remainDays = inPeriod.getRemainDays();
//
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
//
////		// 実特別休暇：使用数
////		double actualUseDays = remainDays.getGrantDetailBefore().getUseDays();
////		SpecialLeaveRemainDay actualUseDaysBefore = new SpecialLeaveRemainDay(
////				remainDays.getGrantDetailBefore().getUseDays());
////		SpecialLeaveRemainDay actualUseDaysAfter = null;
////		if (remainDays.getGrantDetailAfter().isPresent()){
////			actualUseDaysAfter = new SpecialLeaveRemainDay(remainDays.getGrantDetailAfter().get().getUseDays());
////			actualUseDays += actualUseDaysAfter.v();
////		}
////		SpecialLeaveUseDays actualUseNumberDays = new SpecialLeaveUseDays(
////				new SpecialLeaveRemainDay(actualUseDays));
//
//		// 実特別休暇
//		domain.actualSpecial = new SpecialLeave(
//				(actualRemainAfter != null ? actualRemainAfter : actualRemainBefore),
//				actualRemainBefore,
//				new SpecialLeaveUseNumber(
//						actualUseNumberDays,
//						Optional.empty()),
//				Optional.ofNullable(actualRemainAfter));
//
//		SpecialLeaveUsedInfo usedNumberInfoTmp = new SpecialLeaveUsedInfo();
//
//
//
//		SpecialLeaveRemainingNumberInfo remainingNumberInfo = new SpecialLeaveRemainingNumberInfo();
//
//
//
//
////		public static SpecialLeave of(
////				SpecialLeaveUsedInfo usedNumberInfo,
////				SpecialLeaveRemainingNumberInfo remainingNumberInfo){
//
//
//
//
//
//
//
////		付与区分　←　（特別休暇情報（期間終了日の翌日開始時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在する場合）
////		　　　　　　　　　true
////		　　　　　　　　　（存在しない場合）
////		　　　　　　　　　false
////
////		特別休暇付与情報．付与日数←　（特別休暇情報（期間終了日の翌日開始時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在する場合）
////		　　　　　　　　　　　　　　　　　　　　特別休暇情報（期間終了日の翌日開始時点）．付与残数データ．付与日がINPUT．期間．開始＋１日～INPUT．期間．終了日＋１の付与残数データ．明細．付与数．日数
////		　　　　　　　　　　　　　　　　　　　　（存在しない場合）
////		　　　　　　　　　　　　　　　　　　　　NULL
////
//
//
//		// 付与区分
//		domain.grantAtr = inPeriod.getRemainDays().getGrantDetailAfter().isPresent();
//
//		// 付与日数
//		SpecialLeaveGrantUseDay grantDays = null;
//		if (inPeriod.getRemainDays().getGrantDetailAfter().isPresent()){
//			grantDays = new SpecialLeaveGrantUseDay(
//					inPeriod.getRemainDays().getGrantDetailAfter().get().getGrantDays());
//		}
//		domain.grantDays = Optional.ofNullable(grantDays);
//
//		return domain;
		return null;
	}

	public SpecialHolidayRemainData(
			String sid,
			YearMonth ym,
			int closureId,
			ClosureDate closureDate,
			DatePeriod closurePeriod,
			ClosureStatus closureStatus,
			int specialHolidayCd,
			SpecialLeave actualSpecial,
			SpecialLeave specialLeave,
			Optional<SpecialLeaveGrantUseDay> grantDays,
			boolean grantAtr) {
		super();
		this.sid = sid;
		this.ym = ym;
		this.closureId = closureId;
		this.closurePeriod = closurePeriod;
		this.closureStatus = closureStatus;
		this.closureDate = closureDate;
		this.specialHolidayCd = specialHolidayCd;
		this.actualSpecial = actualSpecial;
		this.specialLeave = specialLeave;
		this.grantAtr = grantAtr;
		this.grantDays = grantDays;
	}
}
