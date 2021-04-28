package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class SendMailDialogParam {
	
	private List<String> appIDLst;
	
	private boolean isMultiEmp;
}
