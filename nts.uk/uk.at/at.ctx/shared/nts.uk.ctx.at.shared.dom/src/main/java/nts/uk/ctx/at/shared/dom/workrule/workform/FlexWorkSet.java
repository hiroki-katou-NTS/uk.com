package nts.uk.ctx.at.shared.dom.workrule.workform;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * フレックス勤務の設定
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
public class FlexWorkSet {
	/** 会社ID */
	private CompanyId companyId;
	/** フレックス勤務を管理する */
	private UseAtr useFlexWorkSetting;
	
	/**
	 * Create domain flex work setting
	 * @param companyId
	 * @param useDeformedLabor
	 * @return
	 */
	public static FlexWorkSet createFromJavaType(String companyId, int useFlexWorkSetting) {
		return new FlexWorkSet(new CompanyId(companyId), EnumAdaptor.valueOf(useFlexWorkSetting, UseAtr.class));
	}
}
