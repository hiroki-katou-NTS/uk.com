package nts.uk.ctx.at.request.app.command.application.stamp.command;

import lombok.Data;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
public class AppStampOnlineRecordCmd {
	private Integer stampCombinationAtr;
	
	private Integer appTime;
}
