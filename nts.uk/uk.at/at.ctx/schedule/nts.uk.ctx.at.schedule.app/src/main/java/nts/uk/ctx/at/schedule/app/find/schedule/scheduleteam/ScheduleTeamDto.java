package nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author quytb
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTeamDto {
	/** スケジュールチームコード */
	private String code;
	/** スケジュールチーム名称 */
	private String name;
}
