package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplyTimeAtr {
	/**開始	 */
	START(0, "開始"),
	END(1, "終了"),
	START2(2,"開始2"),
	END2(3,"終了2");
	
	public final Integer value;
	
	public final String name;
}
