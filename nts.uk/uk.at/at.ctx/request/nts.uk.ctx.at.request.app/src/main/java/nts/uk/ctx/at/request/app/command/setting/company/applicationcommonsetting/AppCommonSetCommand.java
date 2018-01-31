package nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class AppCommonSetCommand {
	// 会社ID
	private String companyId;
	// 所属職場名表示
	private int showWkpNameBelong;
}
