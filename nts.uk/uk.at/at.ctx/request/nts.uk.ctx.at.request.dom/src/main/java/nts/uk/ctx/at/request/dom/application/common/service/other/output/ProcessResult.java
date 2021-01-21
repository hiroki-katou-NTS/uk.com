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
public class ProcessResult {
	// thực hiện approve/deny/remand/release thành công hay không
	private boolean isProcessDone;
	private boolean isAutoSendMail;
	// list người gửi mail thành công
	private List<String> autoSuccessMail;
	// list nguời lỗi mail
	private List<String> autoFailMail;
	// list người lỗi khi gửi mail(lỗi server)
	private List<String> autoFailServer;
	private String appID;
	private String reflectAppId;
	
	public ProcessResult() {
		this.isProcessDone = false;
		this.isAutoSendMail = false;
		this.autoSuccessMail = new ArrayList<>();
		this.autoFailMail = new ArrayList<>();
		this.autoFailServer = new ArrayList<>();
		this.appID = "";
		this.reflectAppId = "";
	} 
}
