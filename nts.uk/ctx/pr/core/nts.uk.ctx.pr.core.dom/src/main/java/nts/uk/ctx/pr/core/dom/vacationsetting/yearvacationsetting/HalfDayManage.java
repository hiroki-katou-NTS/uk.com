package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import lombok.Getter;

@Getter
public class HalfDayManage {

	/** The company uniform limit number. */
	private Integer companyUniformLimitNumber;//TODO recheck
	
	/** The manage division. */
	private Manage manageDivision;
	
	/** The reference. */
	private MaxDayReference reference;
	
	/** The limit number. */
	private Integer limitNumber;//TODO recheck
}
