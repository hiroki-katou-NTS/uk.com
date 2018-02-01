package nts.uk.ctx.at.request.app.find.setting.company.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSet;
/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProxyAppSetDto {
	// 会社ID
	private String companyId;
	// 申請種類
	private int appType;
	public static ProxyAppSetDto convertToDto(ProxyAppSet domain){
		return new ProxyAppSetDto(domain.getCompanyId(), domain.getAppType().value);
	}
}
