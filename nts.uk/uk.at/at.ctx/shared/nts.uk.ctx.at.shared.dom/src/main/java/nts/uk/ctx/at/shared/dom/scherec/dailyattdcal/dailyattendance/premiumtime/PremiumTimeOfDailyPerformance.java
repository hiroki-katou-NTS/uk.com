package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PersonCostCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PriceUnit;

/**
 * 日別勤怠の割増時間 (new)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.割増時間.日別勤怠の割増時間
 * 
 * @author nampt
 * 日別実績の割増時間 (old)
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PremiumTimeOfDailyPerformance {
	
	private List<PremiumTime> premiumTimes;
	
	public static PremiumTimeOfDailyPerformance createEmpty() {
		return new PremiumTimeOfDailyPerformance(Collections.emptyList());
	}
	
	/**
	 * 日別勤怠の割増時間の計算
	 * @param personCostCalculation 人件費計算設定
	 * @param dailyRecordDto 日別勤怠コンバーター
	 * @return 日別勤怠の割増時間
	 */
	public static PremiumTimeOfDailyPerformance calcPremiumTime(PersonCostCalculation personCostCalculation,
																DailyRecordToAttendanceItemConverter dailyRecordDto) {
		//個人時間単価を取得する
		//2020.8.24 ichioka ドメインが未確定の為、未実装。
		PriceUnit priceUnit = new PriceUnit(0);
		
		List<PremiumTime> times = personCostCalculation.getPremiumSettings().stream()
			.map(pcc -> PremiumTime.create(dailyRecordDto, priceUnit, personCostCalculation.getRoundingSet(), pcc))
			.collect(Collectors.toList());

		return new PremiumTimeOfDailyPerformance(times);
	}
	
	/**
	 * 日別勤怠の割増時間の計算（応援用）
	 * @param personCostCalculation 人件費計算設定
	 * @param dailyRecordDto 日別勤怠コンバーター
	 * @return 日別勤怠の割増時間
	 */
	public static PremiumTimeOfDailyPerformance calcPremiumTimeForOuen(PersonCostCalculation personCostCalculation,
																		DailyRecordToAttendanceItemConverter dailyRecordDto) {
		//個人時間単価を取得する
		//2020.8.24 ichioka ドメインが未確定の為、未実装。
		PriceUnit priceUnit = new PriceUnit(0);
		
		//割増時間
		List<PremiumTime> times = personCostCalculation.getPremiumSettings().stream()
			.map(pcc -> PremiumTime.createForOuen(dailyRecordDto, priceUnit, personCostCalculation.getRoundingSet(), pcc))
			.collect(Collectors.toList());

		return new PremiumTimeOfDailyPerformance(times);
	}
	
	
	//指定されたNoに一致する割増時間を取得する
	public Optional<PremiumTime> getPremiumTime(int number){
		return this.premiumTimes.stream().filter(tc -> tc.getPremiumTimeNo() == number).findFirst();
	}
	
}
