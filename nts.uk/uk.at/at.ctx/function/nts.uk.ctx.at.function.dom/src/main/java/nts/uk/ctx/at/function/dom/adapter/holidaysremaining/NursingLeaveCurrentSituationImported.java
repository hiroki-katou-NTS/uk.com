package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import lombok.Getter;
import lombok.Setter;

/*
 * 介護休暇現在状況
 */

@Getter
@Setter
public class NursingLeaveCurrentSituationImported {
	/** 使用日数 */
	private String numberOfUse;
	/** 残日数 */
	private String remainingDays;

	public NursingLeaveCurrentSituationImported(String numberOfUse, String remainingDays) {
		super();
		this.numberOfUse = numberOfUse;
		this.remainingDays = remainingDays;
	}
}
