package nts.uk.ctx.at.function.dom.adapter.nursingcareleavemanagement;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NursingCareLeaveImported {
	
	/** 使用日数 */
	private Double usedDays;
	
	/** 残日数 */
	private Double remainDays;

	public NursingCareLeaveImported(Double usedDays, Double remainDays) {
		this.usedDays = usedDays;
		this.remainDays = remainDays;
	}
	
	
}
