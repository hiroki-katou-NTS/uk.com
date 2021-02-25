package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.PersonnelCostSettingImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;

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
	
	
	/**
	 * 割増時間の計算
	 * @param personnelCostSettingImport
	 * @return
	 */
	public static PremiumTimeOfDailyPerformance calcPremiumTime(List<PersonnelCostSettingImport> personnelCostSettingImport,
														 		Optional<DailyRecordToAttendanceItemConverter> dailyRecordDto) {
		
		List<PremiumTime> list = new ArrayList<>();
		
		//人件費設定分ループ
		for(PersonnelCostSettingImport premiumTime : personnelCostSettingImport) {
			list.add(premiumTime.calcPremiumTime(dailyRecordDto));
		}
		
		PremiumTimeOfDailyPerformance result = new PremiumTimeOfDailyPerformance(list);
		return result;
	}
	
	//指定されたNoに一致する割増時間を取得する
	public Optional<PremiumTime> getPremiumTime(int number){
		return this.premiumTimes.stream().filter(tc -> tc.getPremiumTimeNo() == number).findFirst();
	}
	
}
