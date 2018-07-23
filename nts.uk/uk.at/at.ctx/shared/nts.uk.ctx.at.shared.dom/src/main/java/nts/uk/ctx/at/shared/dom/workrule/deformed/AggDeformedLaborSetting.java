package nts.uk.ctx.at.shared.dom.workrule.deformed;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * Domain aggregate deformed labor setting
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
// 変形労働の集計設定
public class AggDeformedLaborSetting {
	
	/** 会社ID */
	private CompanyId companyId;
	/** 変形労働を使用する */
	private UseAtr useDeformedLabor;
	
	/**
	 * Create domain aggregate deformed labor setting
	 * @param companyId
	 * @param useDeformedLabor
	 * @return
	 */
	public static AggDeformedLaborSetting createFromJavaType(String companyId, int useDeformedLabor) {
		return new AggDeformedLaborSetting(new CompanyId(companyId), EnumAdaptor.valueOf(useDeformedLabor, UseAtr.class));
	}
}
