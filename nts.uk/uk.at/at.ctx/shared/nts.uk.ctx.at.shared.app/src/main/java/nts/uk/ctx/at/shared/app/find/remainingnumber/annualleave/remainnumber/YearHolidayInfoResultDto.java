package nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave.remainnumber;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.YearHolidayInfoResult;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YearHolidayInfoResultDto {
	
	// 次回年休付与日
	private String nextGrantDate;

	// 次回年休付与日数
	private String nextGrantDay;

	// 次回時間年休付与上限
	private String nextMaxTime;
	
	public static YearHolidayInfoResultDto fromDomain(YearHolidayInfoResult domain) {
		YearHolidayInfoResultDto result = new YearHolidayInfoResultDto();
		result.nextGrantDate = nextTimeGrantDate(domain.getNextGrantDate());
		result.nextGrantDay = nextTimeGrantDays(domain.getNextGrantDay());
		result.nextMaxTime = nextTimeMaxTime(domain.getNextMaxTime());
		return result;
		
	}
	
	private static String nextTimeGrantDate(GeneralDate nextGrantDate) {
		return nextGrantDate.toString("yyyy/MM/dd");
	}
	
	private static String nextTimeGrantDays(Double nextGrantDay) {
		return nextGrantDay +"日";
	}
	
	private static String nextTimeMaxTime(Optional<Integer> nextMaxTime) {
		if (!nextMaxTime.isPresent()){
			return null;
		}
		Integer hours = nextMaxTime.get() / 60 ;
		Integer minute = nextMaxTime.get() % 60;
		
		return  ((hours == 0 && minute <0) ? ("-" + hours) : hours ) + ":" + (Math.abs(minute) < 10 ? ("0" + Math.abs(minute)) : (Math.abs(minute) + ""));
	}
}
