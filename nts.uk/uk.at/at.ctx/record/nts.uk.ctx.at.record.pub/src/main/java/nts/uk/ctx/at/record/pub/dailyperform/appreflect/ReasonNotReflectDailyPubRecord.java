package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReasonNotReflectDailyPubRecord {
	

	// 問題なし

	NOT_PROBLEM(0, "問題なし"),

	// 実績確定済

	ACTUAL_CONFIRMED(1, "実績確定済");

	public final Integer value;
	public final String name;
}
