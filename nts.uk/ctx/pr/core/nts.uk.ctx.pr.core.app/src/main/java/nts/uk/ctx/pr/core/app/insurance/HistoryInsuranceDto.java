package nts.uk.ctx.pr.core.app.insurance;

import lombok.Data;
import nts.arc.time.YearMonth;

@Data
public class HistoryInsuranceDto{
	private String historyId;
	//private MonthRange monthRage;
	private String startMonthRage;
	private String endMonthRage;
	private String inforMonthRage;
	public static String convertMonth(YearMonth yearMonth) {
		String convert = "";
		String mounth = "";
		if (yearMonth.month() < 10) {
			mounth = "0" + yearMonth.month();
		} else
			mounth = String.valueOf(yearMonth.month());
		convert = yearMonth.year() + "/" + mounth;
		return convert;
	}
}
