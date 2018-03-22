package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TimeOfDayReflectOutput {
	/**	 反映するフラグ*/
	private boolean reflectFlg;
	/**	反映する時刻 */
	private Integer timeOfDay;
}
