package nts.uk.ctx.at.schedule.pub.budget.premium;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCostSettingExport {
	/**
	 * 人件費割増率
	 */
	private Integer premiumRate;
	
	/**
	 * 割増項目NO
	 */
	private Integer premiumNo;
	
	/**
	 * 勤怠項目ID
	 */
	private List<Integer> attendanceItems;
	
	private DatePeriod period;

}
