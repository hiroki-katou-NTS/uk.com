package nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam;

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
public class ScheduleTeamDetailDto extends ScheduleTeamDto {
	/** スケジュールチーム備考 */
	private String note;

	public ScheduleTeamDetailDto(String code, String name, String note) {
		super(code, name);
		this.note = note;
	}

}
