package nts.uk.ctx.at.request.app.command.setting.company.displayname;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HdAppDispNameCommand {
	// 会社ID
	private String companyId;
	// 休暇申請種類
	private int hdAppType;
	// 表示名
	private String dispName;
}
