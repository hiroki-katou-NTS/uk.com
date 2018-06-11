package nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dat.lh
 */
@Data
@AllArgsConstructor
public class MailDestinationImport {
	private String employeeID;

	private List<OutGoingMailImport> outGoingMails;
}
