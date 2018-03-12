package nts.uk.ctx.at.request.app.command.setting.company.applicationsetting;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Proxy App Set Command
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class ProxyAppSetCommand {
	// 申請種類
	private List<Integer> appType;
}
