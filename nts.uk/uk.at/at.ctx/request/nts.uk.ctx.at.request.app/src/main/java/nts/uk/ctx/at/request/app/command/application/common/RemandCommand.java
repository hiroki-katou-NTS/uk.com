package nts.uk.ctx.at.request.app.command.application.common;

import lombok.Getter;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Getter
public class RemandCommand {
	
	private String appID;
	
	private Long version;
	
	private Integer order;
	
	private String returnReason;
	
}
