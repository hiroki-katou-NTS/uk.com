package nts.uk.ctx.at.request.app.command.setting.company.mailsetting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentOfRemandMailCmd {
	// 件名
	private String mailTitle;
	// 本文
	private String mailBody;
}
