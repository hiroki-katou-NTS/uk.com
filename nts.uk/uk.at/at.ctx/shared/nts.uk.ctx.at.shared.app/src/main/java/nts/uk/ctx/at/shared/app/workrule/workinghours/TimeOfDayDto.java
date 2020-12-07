package nts.uk.ctx.at.shared.app.workrule.workinghours;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 時間帯(実装コードなし/使用不可) : dto
 * @author tutk
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeOfDayDto {
	/**
	 * /時刻
	 */
	private Integer time;
	
	/**
	 * 日区分
	 */
	private Integer dayDivision;

}
