package nts.uk.ctx.at.shared.app.workrule.workinghours;

import lombok.Data;

/**
 * 
 * @author tutk
 *
 */
@Data
public class CheckTimeIsIncorrectCmd {
	private String workType;
	private String workTime;
	private TimeZoneDto workTime1;
	private TimeZoneDto workTime2;
}
