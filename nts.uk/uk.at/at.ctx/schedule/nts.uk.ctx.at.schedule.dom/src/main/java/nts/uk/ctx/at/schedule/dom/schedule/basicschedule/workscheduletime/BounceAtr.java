package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime;

import lombok.AllArgsConstructor;

/**
 * 直行直帰区分
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum BounceAtr {
	// 直帰のみ
	NO_DIRECT_BOUNCE(0),
	// 直行直帰
	DIRECTLY_ONLY(1),
	// 直行のみ
	BOUNCE_ONLY(2),
	// 直行直帰なし
	DIRECT_BOUNCE(3);

	public final int value;
}
