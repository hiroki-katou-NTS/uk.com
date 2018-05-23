package nts.uk.ctx.at.record.dom.remainingnumber.base;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DayOffManagement {

	private String subOfHDID;
	private String dayoffDate;
	private Double remainDays;

	public DayOffManagement(String subOfHDID, String dayoffDate, Double remainDays) {
		super();
		this.subOfHDID = subOfHDID;
		this.dayoffDate = dayoffDate;
		this.remainDays = remainDays;
	}
}
