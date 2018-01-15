package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * TanLV
 *
 */
@AllArgsConstructor
public enum RoundingTime {
	/** 0- 1分 **/
	ONE_MINUTE(0),
	/** 1- 5分 **/
	FIVE_MINS(1),
	/** 2- 6分 **/
	SIX_MINS(2),
	/** 3- 10分 **/
	TEN_MINS(3),
	/** 4- 15分 **/
	FIFTEEN_MINS(4),
	/** 4- 20分 **/
	TWENTY_MINS(5),
	/** 4- 30分 **/
	THIRTY_MINS(6),
	/** 4- 60分 **/
	SIXTY_MINS(7);
	
	public final int value;
}
