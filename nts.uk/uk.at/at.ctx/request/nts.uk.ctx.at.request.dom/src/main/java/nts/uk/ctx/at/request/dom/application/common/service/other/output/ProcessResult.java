package nts.uk.ctx.at.request.dom.application.common.service.other.output;
import java.util.List;
import lombok.Getter;

import lombok.AllArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
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
}
