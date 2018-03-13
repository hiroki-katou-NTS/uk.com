package nts.uk.ctx.at.schedule.pub.appreflectprocess;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplyTimeAtrPub {
	/**開始	 */
	START(0, "開始"),
	END(1, "終了"),
	START2(2,"開始2"),
	END2(3,"終了2");
	
	public final Integer value;
	
	public final String name;
}
