package nts.uk.ctx.at.shared.app.command.calculation.setting;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.setting.DeformLaborOT;

/**
 * The class AddDeformLaborOTCommand 
 * @author yennh
 *
 */
@Data
@AllArgsConstructor
public class AddDeformLaborOTCommand {
	/*変形法定内残業を計算する*/
	private BigDecimal legalOtCalc;
	
	public DeformLaborOT toDomain(String cid){
		return DeformLaborOT.createFromJavaType(cid, this.legalOtCalc);
	}
}
