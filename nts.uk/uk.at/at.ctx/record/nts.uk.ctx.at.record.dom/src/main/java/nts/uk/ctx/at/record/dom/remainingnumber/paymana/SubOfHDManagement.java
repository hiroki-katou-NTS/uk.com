package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubOfHDManagement {

	private String payoutId;
	private String dayoffDate;
	private Double remainDays;

	public SubOfHDManagement(String payoutId, String dayoffDate, Double remainDays) {
		super();
		this.payoutId = payoutId;
		this.dayoffDate = dayoffDate;
		this.remainDays = remainDays;
	}
}
