package nts.uk.ctx.at.request.dom.application.timeleaveapplication;

import lombok.AllArgsConstructor;

/**
 * enum : 時間消化申請
 */
@AllArgsConstructor
public enum TimeDigestAppType {

	HOURS_OF_SUB_HOLIDAY(0, "時間代休時間"),

	HOURS_OF_HOLIDAY(1, "時間年休時間"),

	CHILD_NURSING_TIME(2, "子の看護時間"),

	NURSING_TIME(3, "介護時間"),

	SIXTY_H_OVERTIME(4, "60H超休"),

	TIME_SPECIAL_VACATION(5, "時間特別休暇"),

	USE_COMBINATION(6, "組合せ利用");

	public int value;
	
	public String name;
	
}
