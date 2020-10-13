package nts.uk.ctx.workflow.app.find.approvermanagement.setting;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.ApproverRegisterSetDto;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;

@Data
@AllArgsConstructor
public class ApprovalSettingDto {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * 本人による承認
	 */
	private int prinFlg;
	
	// 承認単位の利用設定
	private ApproverRegisterSetDto approverSet;
	
	public static ApprovalSettingDto fromDomain(ApprovalSetting approval) {
		if (approval == null) return null;
		return new ApprovalSettingDto(
				approval.getCompanyId(), 
				BooleanUtils.toInteger(approval.getPrinFlg()), 
				ApproverRegisterSetDto.fromDomain(approval.getApproverRegsterSet()));
	}
}
