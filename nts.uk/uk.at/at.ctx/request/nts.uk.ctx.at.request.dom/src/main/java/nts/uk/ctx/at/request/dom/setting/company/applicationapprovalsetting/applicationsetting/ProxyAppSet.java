package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;

/**
 * 代行申請で利用できる申請設定
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProxyAppSet extends DomainObject{
	// 会社ID
	private String companyId;
	// 申請種類
	private ApplicationType_Old appType;
	
	public static ProxyAppSet createFromJavaType(String companyId, int appType){
		return new ProxyAppSet(companyId, EnumAdaptor.valueOf(appType, ApplicationType_Old.class));
	}
}
