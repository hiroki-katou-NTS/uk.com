package nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave.nexttime;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.YearHolidayInfoResult;

@Data
@NoArgsConstructor
public class NextTimeEventDto {
	
	/**
	 * 次回年休付与日
	 */
	private String nextTimeGrantDate;
	
	/**
	 * 次回年休付与日数
	 */
	private String nextTimeGrantDays;
	
	/**
	 * 次回時間年休付与上限
	 */
	private String nextTimeMaxTime;

	public NextTimeEventDto(String nextTimeGrantDate, String nextTimeGrantDays, String nextTimeMaxTime) {
		super();
		this.nextTimeGrantDate = nextTimeGrantDate;
		this.nextTimeGrantDays = nextTimeGrantDays;
		this.nextTimeMaxTime = nextTimeMaxTime;
	}

	public static NextTimeEventDto fromDomain(YearHolidayInfoResult domain) {
		NextTimeEventDto result = new NextTimeEventDto();
		result.nextTimeGrantDate = nextTimeGrantDate(domain.getNextGrantDate());
		result.nextTimeGrantDays = nextTimeGrantDays(domain.getNextGrantDay());
		result.nextTimeMaxTime = nextTimeMaxTime(domain.getNextMaxTime());
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
