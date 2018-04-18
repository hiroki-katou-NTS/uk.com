package nts.uk.ctx.at.shared.dom.workrule.workuse;

import lombok.Builder;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * The domain temporary work use management
 * 臨時勤務利用管理
 * @author HoangNDH
 *
 */
@Data
@Builder
public class TemporaryWorkUseManage {
	/** 会社ID */
	private CompanyId companyId;
	/** 使用区分 */
	private UseAtr useClassification;
	
	/**
	 * Creates domain from java type.
	 *
	 * @param companyId the company id
	 * @param useClassification the use classification
	 * @return the temporary work use manage
	 */
	public static TemporaryWorkUseManage createFromJavaType(String companyId, int useClassification) {
		return new TemporaryWorkUseManage(new CompanyId(companyId), EnumAdaptor.valueOf(useClassification, UseAtr.class));
	}
}
