package nts.uk.ctx.at.request.dom.application.common.service.other.output;

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
public class MailResult {
	private List<String> successList;
	private List<String> failList;
}
