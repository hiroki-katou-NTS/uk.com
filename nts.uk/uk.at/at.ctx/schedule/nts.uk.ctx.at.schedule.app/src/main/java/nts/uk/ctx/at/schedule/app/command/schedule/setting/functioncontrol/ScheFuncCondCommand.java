package nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncCond;
import nts.uk.shr.com.context.AppContexts;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheFuncCondCommand {
	/** 条件NO */
	private int conditionNo;
	
	/**
	 * To domain
	 * @param companyId
	 * @param conditionNo
	 * @return
	 */
	public ScheFuncCond toDomain(int conditionNo){
		String companyId = AppContexts.user().companyId();
		return ScheFuncCond.createFromJavaType(companyId, conditionNo);
 	}
}
