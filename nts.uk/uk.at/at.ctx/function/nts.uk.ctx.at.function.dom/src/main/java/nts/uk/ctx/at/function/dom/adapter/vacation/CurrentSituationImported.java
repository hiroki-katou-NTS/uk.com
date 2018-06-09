package nts.uk.ctx.at.function.dom.adapter.vacation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentSituationImported {
	/*
	 * 使用日数
	 */
	private Double usedDate;
	/*
	 * 残日数
	 */
	private Double remainDate;
	

	public CurrentSituationImported(Double usedDate, Double remainDate) {
		super();
		this.usedDate = usedDate;
		this.remainDate = remainDate;
	}

}
