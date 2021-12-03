package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FakeSupportType {
	
	/**
	 * 終日応援
	 */
	FULL_DAY_SUPPORT(0, "all day"), // TODO: create name

	/**
	 * 時間帯応援
	 */
	TIME_SPAN_SUPPORT(1, "time span support"); // TODO: create name
	
	public final int value;

	public final String name;

}
