package nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.ApprovalSet;

/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApprovalSetDto {
	public String companyId;
	/**　スケジュールが確定されている場合 */
	public int scheduleCon;
	/**　実績が確定されている場合 */
	public int achiveCon; 
	
	public static ApprovalSetDto convertToDto(ApprovalSet domain){
		return new ApprovalSetDto(domain.getCompanyId(), 
				domain.getScheduleCon().value, domain.getAchiveCon().value);
	}
}
