package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import lombok.Getter;
import lombok.Setter;

/*
 * 看護休暇現在状況
 */

@Getter
@Setter
public class ChildNursingLeaveCurrentSituationImported {
	/** 使用日数 */
	private String numberOfUse;
	/** 残日数 */
	private String remainingDays;

	public ChildNursingLeaveCurrentSituationImported(String numberOfUse, String remainingDays) {
		super();
		this.numberOfUse = numberOfUse;
		this.remainingDays = remainingDays;
	}
}
