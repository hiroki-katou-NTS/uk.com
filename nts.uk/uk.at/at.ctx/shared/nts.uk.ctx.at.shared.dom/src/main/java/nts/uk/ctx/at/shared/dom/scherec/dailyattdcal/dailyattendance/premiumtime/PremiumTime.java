package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PersonCostRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PremiumSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PriceUnit;

@Getter
@AllArgsConstructor
/**　nts.uk.ctx.at.record.dom.premiumtime.PremiumTime　参照 */
/** 割増時間 */
public class PremiumTime {
	// 割増時間NO - primitive value
	private int premiumTimeNo;

	// 割増時間
	private AttendanceTime premitumTime;
	
	/** 割増金額 */
	private AttendanceAmountDaily premiumAmount;
	
	/** フレックス時間（勤怠項目ID） */
	private static final Integer FLEX_TIME_ITEM_ID = 556;
	
	/**
	 * デフォルト値を返す
	 * @param premiumTimeNo 割増時間NO
	 * @return 割増時間
	 */
	public static PremiumTime defaultValue(Integer premiumTimeNo) {
		return new PremiumTime(premiumTimeNo, AttendanceTime.ZERO, AttendanceAmountDaily.ZERO);
	}
	
	/**
	 * 割増時間計算
	 * @param dailyRecordDto 日別勤怠コンバーター
	 * @param priceUnit 単価
	 * @param roundingSet 人件費丸め設定
	 * @param premiumSetting 割増設定
	 * @return 割増時間
	 */
	public static PremiumTime create(DailyRecordToAttendanceItemConverter dailyRecordDto,
			PriceUnit priceUnit, PersonCostRoundingSetting roundingSet, PremiumSetting premiumSetting) {
		if(premiumSetting.getAttendanceItems().isEmpty()) {
			return PremiumTime.defaultValue(premiumSetting.getDisplayNumber());
		}
		
		//割増時間
		AttendanceTime time = new AttendanceTime(dailyRecordDto.convert(premiumSetting.getAttendanceItems()).stream()
				.filter(c -> c.value() != null)
				.mapToInt(r -> (int)r.value())
				.sum());
		
		//割増金額
		AttendanceAmountDaily amount = calcPremiumAmounts(priceUnit, premiumSetting.getRate(), time, roundingSet);
		
		return new PremiumTime(premiumSetting.getDisplayNumber(), time, amount);
	}
	
	/**
	 * 割増時間計算（応援用）
	 * @param dailyRecordDto 日別勤怠コンバーター
	 * @param priceUnit 単価
	 * @param roundingSet 人件費丸め設定
	 * @param premiumSetting 割増設定
	 * @return 割増時間
	 */
	public static PremiumTime createForOuen(DailyRecordToAttendanceItemConverter dailyRecordDto,
			PriceUnit priceUnit, PersonCostRoundingSetting roundingSet, PremiumSetting premiumSetting) {
		if(premiumSetting.getAttendanceItems().isEmpty()) {
			return PremiumTime.defaultValue(premiumSetting.getDisplayNumber());
		}
		
		//割増時間
		AttendanceTime time = new AttendanceTime(dailyRecordDto.convert(premiumSetting.getAttendanceItems()).stream()
				.filter(c -> c.value() != null && c.getItemId() != FLEX_TIME_ITEM_ID) //フレックス時間を除く
				.mapToInt(r -> (int)r.value())
				.sum());
		
		//割増金額
		AttendanceAmountDaily amount = calcPremiumAmounts(priceUnit, premiumSetting.getRate(), time, roundingSet);
		
		return new PremiumTime(premiumSetting.getDisplayNumber(), time, amount);
	}
	
	/**
	 * 割増金額を計算する
	 * @param priceUnit 単価
	 * @param premiumRate 割増率
	 * @param premiumTime 割増時間
	 * @param roundingSet 人件費丸め設定
	 * @return 割増金額
	 */
	private static AttendanceAmountDaily calcPremiumAmounts(PriceUnit priceUnit, PremiumRate premiumRate,
			AttendanceTime premiumTime, PersonCostRoundingSetting roundingSet) {
		// A = 単価 * 割増率
		int afterPremium = priceUnit.v() * premiumRate.v();
		// Aを丸める
		BigDecimal afterPremiumRounding = roundingSet.getUnitPrice().round(BigDecimal.valueOf(afterPremium));
		// B = A * 割増時間
		BigDecimal amount = afterPremiumRounding.multiply(BigDecimal.valueOf(premiumTime.valueAsMinutes()));
		// Bを丸める
		BigDecimal afterAmountRounding = roundingSet.getCost().round(amount);
		// Bを返す
		return  new AttendanceAmountDaily(afterAmountRounding.intValue());
	}
}
