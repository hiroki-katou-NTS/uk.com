package nts.uk.ctx.at.request.app.command.application.stamp.command;

import lombok.Data;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
public class AppStampGoOutPermitCmd {
	private Integer stampAtr;
	
	private Integer stampFrameNo;
	
	private Integer stampGoOutAtr;
	
	private Integer startTime;
	
	private String startLocation;
	
	private Integer endTime;
	
	private String endLocation;
}
