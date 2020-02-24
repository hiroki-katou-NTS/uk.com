package nts.uk.ctx.at.schedule.dom.shift.management;

import lombok.Getter;

/**
 * 対象組織
 * @author phongtq
 *
 */

public class ShiftTargeOrg {
	
	@Getter
	/** 対象組織の単位    0:職場    1:職場グループ */
	public int targetUnit;

	@Getter
	/** 対象組織の単位に応じたID  0:職場ID  1:職場グループID */
	public String targetId;
}

