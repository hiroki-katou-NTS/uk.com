package nts.uk.ctx.at.request.app.command.application.stamp.command;

import lombok.Data;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
public class AppStampCancelCmd {
	private Integer stampAtr;
	
	private Integer stampFrameNo;
	
	private Integer cancelAtr;
}
