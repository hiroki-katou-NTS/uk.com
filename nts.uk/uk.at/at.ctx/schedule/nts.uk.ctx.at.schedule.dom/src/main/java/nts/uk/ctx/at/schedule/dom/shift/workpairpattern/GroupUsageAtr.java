package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import lombok.AllArgsConstructor;

/**
 * グループ使用区分
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum GroupUsageAtr {
	// しない
	NOT_USE(0),
	// する
	USE(1);

	public final int value;
}
