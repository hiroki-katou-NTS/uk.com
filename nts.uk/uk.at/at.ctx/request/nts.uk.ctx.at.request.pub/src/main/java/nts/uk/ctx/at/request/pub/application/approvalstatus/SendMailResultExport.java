package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class SendMailResultExport {
	private boolean OK;
	private List<String> listError;
}
