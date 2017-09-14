package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.Data;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
public class AppStampCancelDto {
	private Integer stampAtr;
	
	private Integer stampFrameNo;
	
	private Integer cancelAtr;
}
