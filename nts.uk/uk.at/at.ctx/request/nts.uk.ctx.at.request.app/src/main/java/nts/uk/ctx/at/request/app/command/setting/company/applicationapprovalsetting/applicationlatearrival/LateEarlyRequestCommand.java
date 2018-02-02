package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LateEarlyRequestCommand {
	/** * 会社ID */
	public String companyId;
	
	/** * 実績を表示する */
	public int showResult;
}
