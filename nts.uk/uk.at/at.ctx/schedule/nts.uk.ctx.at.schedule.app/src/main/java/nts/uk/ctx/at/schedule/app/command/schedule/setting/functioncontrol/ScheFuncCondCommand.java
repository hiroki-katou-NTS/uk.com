package nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncCond;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheFuncCondCommand {
	/** 会社ID */
	private String companyId;
	
	/** 条件NO */
	private int conditionNo;
	
	/**
	 * To domain
	 * @param companyId
	 * @param conditionNo
	 * @return
	 */
	public ScheFuncCond toDomain(String companyId, int conditionNo){
		return ScheFuncCond.createFromJavaType(companyId, conditionNo);
 	}
}
