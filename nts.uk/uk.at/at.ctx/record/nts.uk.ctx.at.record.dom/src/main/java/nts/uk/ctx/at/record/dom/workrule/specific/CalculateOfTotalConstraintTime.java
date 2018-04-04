package nts.uk.ctx.at.record.dom.workrule.specific;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * 総拘束時間の計算.
 *
 * @author HoangNDH
 */
@Data
@AllArgsConstructor
public class CalculateOfTotalConstraintTime {
	
	/** The company id. */
	// 会社ID
	private CompanyId companyId;
	
	/** The calc method. */
	// 計算方法
	private CalculationMethodOfConstraintTime calcMethod;
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param calcMethod the calc method
	 * @return the calculate of total constraint time
	 */
	public static CalculateOfTotalConstraintTime createFromJavaType(String companyId, int calcMethod) {
		return new CalculateOfTotalConstraintTime(new CompanyId(companyId), EnumAdaptor.valueOf(calcMethod, CalculationMethodOfConstraintTime.class));
	}
}
