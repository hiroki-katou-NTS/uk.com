package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

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
public class MailSenderResult {
	
	private List<String> successList;
	
	private List<String> errorList;
	
}
