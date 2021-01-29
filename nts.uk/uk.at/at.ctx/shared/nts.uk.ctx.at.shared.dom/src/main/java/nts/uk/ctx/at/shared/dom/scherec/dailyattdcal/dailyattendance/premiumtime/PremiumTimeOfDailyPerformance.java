package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
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

	/** 割増時間 **/
	private List<PremiumTime> premiumTimes;


	/** 割増金額合計 **/
	private AttendanceAmountDaily totalAmount;

	/** 割増労働時間合計 **/
	private AttendanceTime totalWorkingTime;


	// TODO 既存コード用に追加 要修正
	public PremiumTimeOfDailyPerformance(List<PremiumTime> premiumTimes) {
		this.premiumTimes = premiumTimes;
		this.totalAmount = new AttendanceAmountDaily( 0 );
		this.totalWorkingTime = new AttendanceTime( 0 );
	}


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
