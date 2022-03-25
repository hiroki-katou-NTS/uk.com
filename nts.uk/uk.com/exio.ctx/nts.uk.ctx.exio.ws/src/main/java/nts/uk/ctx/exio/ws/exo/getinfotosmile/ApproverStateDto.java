package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import lombok.Data;
import nts.arc.time.GeneralDateTime;
@Data
public class ApproverStateDto {
	private String approverID;
	private int approvalAtr;
	private String agentID;
	private String approverName;
	private String agentName;
	private String representerID;
	private String representerName;
	private GeneralDateTime approvalDate;
	private String approvalReason;
	private String approverEmail;
	private String agentMail;
	private String representerEmail;
	private Integer approverInListOrder;
	
	public ApproverStateDto(String approverID, int approvalAtr, String agentID, String approverName, String agentName,
			String representerID, String representerName, GeneralDateTime approvalDate, String approvalReason,
			String approverEmail, String agentMail, String representerEmail, Integer approverInListOrder) {
		super();
		this.approverID = approverID;
		this.approvalAtr = approvalAtr;
		this.agentID = agentID;
		this.approverName = approverName;
		this.agentName = agentName;
		this.representerID = representerID;
		this.representerName = representerName;
		this.approvalDate = approvalDate;
		this.approvalReason = approvalReason;
		this.approverEmail = approverEmail;
		this.agentMail = agentMail;
		this.representerEmail = representerEmail;
		this.approverInListOrder = approverInListOrder;
	}
	
	
}
