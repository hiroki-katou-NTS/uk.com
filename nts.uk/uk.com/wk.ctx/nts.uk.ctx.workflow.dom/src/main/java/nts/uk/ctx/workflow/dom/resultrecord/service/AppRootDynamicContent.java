package nts.uk.ctx.workflow.dom.resultrecord.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootDynamic;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootDynamicContent {
	
	private AppRootDynamic AppRootDynamic;
	
	private ErrorFlag errorFlag;
	
	private String errorMsgID;
	
}
