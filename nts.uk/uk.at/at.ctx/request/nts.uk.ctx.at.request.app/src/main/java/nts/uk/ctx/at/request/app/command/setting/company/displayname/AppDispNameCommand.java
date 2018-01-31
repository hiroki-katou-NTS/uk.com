package nts.uk.ctx.at.request.app.command.setting.company.displayname;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppDispNameCommand {
	// 会社ID
	private String companyId;
	// 申請種類
	private int appType;
	// 表示名
	private String dispName;
}
