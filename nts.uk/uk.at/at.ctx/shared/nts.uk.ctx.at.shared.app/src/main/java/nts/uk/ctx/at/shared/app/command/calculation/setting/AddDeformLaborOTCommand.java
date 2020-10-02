package nts.uk.ctx.at.shared.app.command.calculation.setting;


import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOT;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class AddDeformLaborOTCommand 
 * @author yennh
 *
 */
@Data
@AllArgsConstructor
public class AddDeformLaborOTCommand {
	/*変形法定内残業を計算する*/
	private NotUseAtr legalOtCalc;
	
	public DeformLaborOT toDomain(String cid){
		return DeformLaborOT.createFromJavaType(cid, this.legalOtCalc);
	}
}
