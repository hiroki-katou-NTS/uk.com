package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class ApproveProcessResult {
	private boolean isProcessDone;
	private boolean isAutoSendMail;
	private List<String> autoSuccessMail;
	private List<String> autoFailMail;
	private List<String> autoFailServer;
	private String appID;
	private String reflectAppId;
	private String appReason;
	
	public ApproveProcessResult() {
		this.isProcessDone = false;
		this.isAutoSendMail = false;
		this.autoSuccessMail = new ArrayList<>();
		this.autoFailMail = new ArrayList<>();
		this.autoFailServer = new ArrayList<>();
		this.appID = "";
		this.reflectAppId = "";
		this.appReason = "";
	}
}
