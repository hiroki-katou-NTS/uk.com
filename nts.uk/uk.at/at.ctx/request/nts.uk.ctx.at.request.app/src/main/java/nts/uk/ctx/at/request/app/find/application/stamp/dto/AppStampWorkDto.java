package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.Data;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
public class AppStampWorkDto {
	private Integer stampAtr;
	
	private Integer stampFrameNo;
	
	private Integer stampGoOutReason;
	
	private String supportCard;
		
	private String supportLocationCD;
	
	private Integer startTime;
	
	private String startLocation;
	
	private Integer endTime;
	
	private String endLocation;
}
