package nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncCond;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheFuncCondDto {
	/** 会社ID */
	private String companyId;
	
	/** 条件NO */
	private int conditionNo;
	
	/**
	 * From domain
	 * 
	 * @param domain
	 * @return
	 */
	public static ScheFuncCondDto fromDomain(ScheFuncCond domain){
		
		return new ScheFuncCondDto(
				domain.getCompanyId(),
				domain.getConditionNo()
		);
	}
}
