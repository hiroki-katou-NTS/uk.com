package nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.remaininggrantdata.checklastgrantnumber.calcumulativegrant;

import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

/**
 * 年休累積付与情報
 * @author tutk
 *
 */
@Data
@Getter
public class CumulativeGrant {
	/**付与日数*/
	private double numberGrantDay;
	
	/**期間*/
	private Period period;

	public CumulativeGrant(double numberGrantDay, Period period) {
		super();
		this.numberGrantDay = numberGrantDay;
		this.period = period;
	}
	
}
