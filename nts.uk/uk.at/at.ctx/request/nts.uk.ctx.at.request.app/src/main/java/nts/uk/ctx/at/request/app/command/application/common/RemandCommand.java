package nts.uk.ctx.at.request.app.command.application.common;


import java.util.List;

import lombok.Getter;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Getter
public class RemandCommand {
	
	private List<String> appID;
	
	private Long version;
	
	private Integer order;
	
	private String returnReason;
	
}
