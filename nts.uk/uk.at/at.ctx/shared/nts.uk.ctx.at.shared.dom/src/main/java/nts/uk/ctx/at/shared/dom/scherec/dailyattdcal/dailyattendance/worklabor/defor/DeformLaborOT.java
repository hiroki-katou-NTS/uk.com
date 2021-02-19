package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 変形労働の法定内残業計算
 * @author yennh
 *
 */
@AllArgsConstructor
@Getter
public class DeformLaborOT extends DomainObject {
	/*会社ID*/
	private String cid;

	/*変形法定内残業を計算する*/
	private NotUseAtr legalOtCalc;

	public static DeformLaborOT createFromJavaType(String cid, int legalOtCalc) {
		return new DeformLaborOT(cid, NotUseAtr.valueOf(legalOtCalc));
	}
	
	/**
	 * 変形法定内残業を計算する
	 * @return true：計算する false：計算しない
	 */
	public boolean isLegalOtCalc() {
		return this.legalOtCalc.equals(NotUseAtr.USE);
	}
}
