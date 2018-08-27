package nts.uk.ctx.workflow.dom.resultrecord.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstance;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootInstanceContent {
	
	private AppRootInstance appRootInstance;
	
	private ErrorFlag errorFlag;
	
	private String errorMsgID;
	
}
