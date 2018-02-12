package nts.uk.ctx.at.request.app.command.setting.company.mailsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class MailHdInstructionCommand {
	// 会社ID
	private String companyId;
	// 件名
	private String subject;
	// 本文
	private String content;
}
