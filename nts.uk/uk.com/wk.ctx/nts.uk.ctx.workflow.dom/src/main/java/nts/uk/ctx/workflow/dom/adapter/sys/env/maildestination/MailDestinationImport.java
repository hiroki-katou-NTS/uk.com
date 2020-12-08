package nts.uk.ctx.workflow.dom.adapter.sys.env.maildestination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author dat.lh
 */
@Data
@AllArgsConstructor
public class MailDestinationImport {
	private String employeeID;

	private List<String> outGoingMails;
}
