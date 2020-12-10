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
	public String companyId;
	/**
	 * 本人による承認
	 */
	public int prinFlg;
	
	// 承認単位の利用設定
	public ApproverRegisterSetDto approverSet;
	
	public static ApprovalSettingDto fromDomain(ApprovalSetting approval) {
		if (approval == null) return null;
		return new ApprovalSettingDto(
				approval.getCompanyId(), 
				BooleanUtils.toInteger(approval.getPrinFlg()), 
				ApproverRegisterSetDto.fromDomain(approval.getApproverRegsterSet()));
	}
	public ApprovalSettingDto(String companyId, Integer prinFlg) {
		this.companyId = companyId;
		this.prinFlg = prinFlg;
	}
	
}
