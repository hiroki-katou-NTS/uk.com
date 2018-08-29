package nts.uk.ctx.workflow.pub.resultrecord.export;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootInsContentExport {
	
	private AppRootInsExport appRootInstance;
	
	private Integer errorFlag;
	
	private String errorMsgID;
	
}
