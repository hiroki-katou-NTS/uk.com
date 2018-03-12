package nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TimeOfDayReflectFindDto {
	/**	 反映するフラグ*/
	private boolean reflectFlg;
	/**	反映する時刻 */
	private Integer timeOfDay;
}
