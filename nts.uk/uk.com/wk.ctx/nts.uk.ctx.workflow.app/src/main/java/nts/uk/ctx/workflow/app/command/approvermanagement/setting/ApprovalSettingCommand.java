package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.shr.com.context.AppContexts;

@Data
@AllArgsConstructor
public class ApprovalSettingCommand {
	/**
	 * 本人による承認
	 */
	private int prinFlg;
	private ApprovalSetting toDomain(){
		String companyId = AppContexts.user().companyId();
		ApprovalSetting appro = ApprovalSetting.createFromJavaType(companyId, this.prinFlg);
		return appro;
	}
}
