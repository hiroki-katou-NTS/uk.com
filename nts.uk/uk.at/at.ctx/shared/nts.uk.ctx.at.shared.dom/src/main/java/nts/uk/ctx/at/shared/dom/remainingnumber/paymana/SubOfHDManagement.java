package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubOfHDManagement {

	private String payoutId;
	private String dayoffDate;
	private Double occurredDays;
	private Double unUsedDays;

	public SubOfHDManagement(String payoutId, String dayoffDate, Double occurredDays, Double unUsedDays) {
		super();
		this.payoutId = payoutId;
		this.dayoffDate = dayoffDate;
		this.occurredDays = occurredDays;
		this.unUsedDays = unUsedDays;
	}
}
