package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dat.lh
 *
 */
@Data
@NoArgsConstructor
public class SendMailResultOutput {
	private boolean OK;
	private List<String> listError;
}
