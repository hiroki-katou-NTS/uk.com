package nts.uk.ctx.at.shared.dom.schedule;

import lombok.AllArgsConstructor;

/**
 * 稼働日区分
 * @author tutk
 *
 */
@AllArgsConstructor
public enum WorkingDayCategory {
	//稼働日
	workingDay(0),
	//法定休日
	nonWorkingDay_inlaw(1),
	//法定外休日
	nonWorkingDay_Outrage(2);
	
	public final int value;
}
