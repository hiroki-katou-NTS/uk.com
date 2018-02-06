/**
 * 5:18:35 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRange {
	private GeneralDate startDate;
	private GeneralDate endDate;

	public List<GeneralDate> toListDate() {
		List<GeneralDate> lstDate = new ArrayList<>();
		GeneralDate element = this.startDate;
		while (element.beforeOrEquals(this.endDate)) {
			lstDate.add(element);
			element = element.addDays(1);
		}
		return lstDate;
	}
}
