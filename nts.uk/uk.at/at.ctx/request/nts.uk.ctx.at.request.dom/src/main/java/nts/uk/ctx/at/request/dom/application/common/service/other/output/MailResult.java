package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class MailResult {
	// list người gửi mail thành công
	private List<String> successList;
	// list nguời lỗi mail
	private List<String> failList;
	// list người lỗi khi gửi mail(lỗi server)
	private List<String> failServerList;
}
