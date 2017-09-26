package nts.uk.ctx.at.request.app.command.application.common.approveaccepted;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class ApproveAcceptedCmd {
	public String phaseID ;
	public int dispOrder ;
	public String approverSID ;
}
