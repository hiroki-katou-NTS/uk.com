package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;

@AllArgsConstructor
public class ApprovalSettingCommand {
	/**
	 * 会社ID
	 */
	public String companyId;
	/**
	 * 本人による承認
	 */
	public int prinFlg;
	
	// 承認単位の利用設定
	public ApproverRegisterSetCommand approverSet;
	
	public ApprovalSetting toDomain() {	
		return new ApprovalSetting(
				companyId,
				approverSet.toDomain(),
				BooleanUtils.toBoolean(prinFlg));
	}
}
