package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.EmployeeUnitPriceHistoryItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.UnitPrice;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.ExtraTimeItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PersonCostCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PersonCostRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.WorkingHoursUnitPrice;

@Getter
@AllArgsConstructor
/**　nts.uk.ctx.at.record.dom.premiumtime.PremiumTime　参照 */
/** 割増時間 */
public class PremiumTime {
	// 割増時間NO - primitive value
	private ExtraTimeItemNo premiumTimeNo;

	// 割増時間
	private AttendanceTime premitumTime;

	/** 割増金額 */
	private AttendanceAmountDaily premiumAmount;

	/** 単価 **/
	private WorkingHoursUnitPrice unitPrice;


	public PremiumTime(ExtraTimeItemNo premiumTimeNo, AttendanceTime premitumTime, AttendanceAmountDaily premiumAmount) {
		super();
		this.premiumTimeNo = premiumTimeNo;
		this.premitumTime = premitumTime;
		this.premiumAmount = premiumAmount;
	}
	
	/**
	 * 全て0で作成する
	 * @param premiumTimeNo 割増時間NO
	 * @return 割増時間
	 */
	public static PremiumTime createAllZero(ExtraTimeItemNo premiumTimeNo) {
		return new PremiumTime(premiumTimeNo, AttendanceTime.ZERO, AttendanceAmountDaily.ZERO, WorkingHoursUnitPrice.ZERO);
	}
	
	/**
	 * 割増時間計算
	 * @param dailyRecordDto 日別勤怠コンバーター
	 * @param unitPriceHistory 社員単価履歴項目
	 * @param personCostCalculation 人件費計算設定
	 * @param premiumSetting 処理中の割増設定
	 * @return 割増時間
	 */
	public static PremiumTime create(
			DailyRecordToAttendanceItemConverter dailyRecordDto,
			Optional<EmployeeUnitPriceHistoryItem> unitPriceHistory,
			PersonCostCalculation personCostCalculation,
			PremiumSetting premiumSetting) {
		if(premiumSetting.getAttendanceItems().isEmpty()) {
			return PremiumTime.createAllZero(premiumSetting.getID());
		}
		
		//割増時間
		AttendanceTime time = new AttendanceTime(dailyRecordDto.convert(premiumSetting.getAttendanceItems()).stream()
				.filter(c -> c.value() != null)
				.mapToInt(r -> (int)r.value())
				.sum());
		
		//社員時間単価を取得する
		WorkingHoursUnitPrice priceUnit = getWorkingHoursUnitPrice(premiumSetting.getID(), personCostCalculation, unitPriceHistory);
		
		//割増金額
		AttendanceAmountDaily amount = calcPremiumAmount(priceUnit, premiumSetting.getRate(), time, personCostCalculation.getRoundingSetting());
		
		return new PremiumTime(premiumSetting.getID(), time, amount, priceUnit);
	}
	
	/**
	 * 割増時間計算（応援用）
	 * @param dailyRecordDto 日別勤怠コンバーター
	 * @param unitPriceHistory 社員単価履歴項目
	 * @param personCostCalculation 人件費計算設定
	 * @param premiumSetting 処理中の割増設定
	 * @return 割増時間
	 */
	public static PremiumTime createForSupport(
			DailyRecordToAttendanceItemConverter dailyRecordDto,
			Optional<EmployeeUnitPriceHistoryItem> unitPriceHistory,
			PersonCostCalculation personCostCalculation,
			PremiumSetting premiumSetting) {
		if(premiumSetting.getAttendanceItems().isEmpty()) {
			return PremiumTime.createAllZero(premiumSetting.getID());
		}
		
		final int FLEX_TIME_ITEM_ID = 556; //フレックス時間（勤怠項目ID）
		
		//割増時間
		AttendanceTime time = new AttendanceTime(dailyRecordDto.convert(premiumSetting.getAttendanceItems()).stream()
				.filter(c -> c.value() != null && c.getItemId() != FLEX_TIME_ITEM_ID) //フレックス時間を除く
				.mapToInt(r -> (int)r.value())
				.sum());
		
		//社員時間単価を取得する
		WorkingHoursUnitPrice priceUnit = getWorkingHoursUnitPrice(premiumSetting.getID(), personCostCalculation, unitPriceHistory);
		
		//割増金額
		AttendanceAmountDaily amount = calcPremiumAmount(priceUnit, premiumSetting.getRate(), time, personCostCalculation.getRoundingSetting());
		
		return new PremiumTime(premiumSetting.getID(), time, amount, priceUnit);
	}
	
	/**
	 * 割増金額を計算する
	 * @param priceUnit 単価
	 * @param premiumRate 割増率
	 * @param premiumTime 割増時間
	 * @param roundingSet 人件費丸め設定
	 * @return 割増金額
	 */
	public static AttendanceAmountDaily calcPremiumAmount(WorkingHoursUnitPrice priceUnit, PremiumRate premiumRate,
			AttendanceTime premiumTime, PersonCostRoundingSetting roundingSet) {
		// A = 単価 * 割増率
		int afterPremium = priceUnit.v() * premiumRate.v();
		// Aを丸める
		BigDecimal afterPremiumRounding = roundingSet.getRoundingOfPremium().round(BigDecimal.valueOf(afterPremium));
		// B = A * 割増時間
		BigDecimal amount = afterPremiumRounding.multiply(BigDecimal.valueOf(premiumTime.valueAsMinutes()));
		// Bを丸める
		BigDecimal afterAmountRounding = roundingSet.getAmountRoundingSetting().round(amount);
		// Bを返す
		return  new AttendanceAmountDaily(afterAmountRounding.intValue());
	}
	
	/**
	 * 社員時間単価を取得する
	 * @param no 割増時間項目NO
	 * @param personCostCalculation 人件費計算設定
	 * @param unitPriceHistory 社員単価履歴項目
	 * @return 社員時間単価
	 */
	private static WorkingHoursUnitPrice getWorkingHoursUnitPrice(
			ExtraTimeItemNo no, PersonCostCalculation personCostCalculation, Optional<EmployeeUnitPriceHistoryItem> unitPriceHistory) {
		Optional<UnitPrice> unitPriceNo = personCostCalculation.getUnitPriceAsJudged(no);
		if(!unitPriceNo.isPresent()) {
			return WorkingHoursUnitPrice.ZERO;
		}
		if(!unitPriceHistory.isPresent()) {
			return WorkingHoursUnitPrice.ZERO;
		}
		return unitPriceHistory.get().getWorkingHoursUnitPrice(unitPriceNo.get()).orElse(WorkingHoursUnitPrice.ZERO);
	}
}
