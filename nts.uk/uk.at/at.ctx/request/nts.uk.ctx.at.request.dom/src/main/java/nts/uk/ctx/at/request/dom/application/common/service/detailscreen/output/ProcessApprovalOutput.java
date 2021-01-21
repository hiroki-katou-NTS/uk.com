package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class ProcessApprovalOutput {
	
	private String applicant;
	
	private List<String> approverLst;
	
	private String appID;
	
	private String reflectAppId;
	
	public ProcessApprovalOutput() {
		this.applicant = "";
		this.approverLst = new ArrayList<>();
		this.appID = "";
		this.reflectAppId = "";
	}
	
}
