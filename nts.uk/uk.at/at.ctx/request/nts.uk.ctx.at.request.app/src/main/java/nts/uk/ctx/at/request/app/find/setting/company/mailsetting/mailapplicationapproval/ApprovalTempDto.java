package nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailapplicationapproval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTemp;
/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApprovalTempDto {
	// 会社ID
	public String companyId;
	// 本文
	public String content;
	public static ApprovalTempDto convertToDto(ApprovalTemp domain){
		return new ApprovalTempDto(domain.getCompanyId(), domain.getContent().v());
	}
}
