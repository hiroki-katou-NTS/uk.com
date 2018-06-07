package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DayOffManagement {

	private String subOfHDID;
	private String dayoffDate;
	private Double requiredDays;

	public DayOffManagement(String subOfHDID, String dayoffDate, Double requiredDays) {
		super();
		this.subOfHDID = subOfHDID;
		this.dayoffDate = dayoffDate;
		this.requiredDays = requiredDays;
	}
}
