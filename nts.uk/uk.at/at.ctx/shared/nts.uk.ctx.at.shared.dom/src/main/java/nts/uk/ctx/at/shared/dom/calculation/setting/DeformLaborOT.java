package nts.uk.ctx.at.shared.dom.calculation.setting;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author yennh
 *
 */
@AllArgsConstructor
@Getter
public class DeformLaborOT extends DomainObject {
	/*会社ID*/
	private String cid;

	/*変形法定内残業を計算する*/
	private BigDecimal legalOtCalc;

	public static DeformLaborOT createFromJavaType(String cid, BigDecimal legalOtCalc) {
		return new DeformLaborOT(cid, legalOtCalc);
	}
}
