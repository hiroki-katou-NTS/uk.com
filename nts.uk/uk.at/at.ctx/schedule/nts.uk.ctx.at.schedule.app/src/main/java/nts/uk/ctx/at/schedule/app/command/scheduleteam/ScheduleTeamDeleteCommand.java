package nts.uk.ctx.at.schedule.app.command.scheduleteam;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author quytb
 *
 */

@Getter
@Setter
public class ScheduleTeamDeleteCommand {
	/** チームコード */
	private String code;
	/** 職場グループID */
	private String workplaceGroupId;

}
