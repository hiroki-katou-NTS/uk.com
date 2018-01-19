package nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 完了実行条件
 * 
 * @author TanLV
 *
 */
@Getter
@AllArgsConstructor
public class ScheFuncCond extends DomainObject {
	/** 会社ID */
	private String companyId;
	
	/** 条件NO */
	private int conditionNo;
	
	/**
	 * Create From Java Type
	 * 
	 * @param companyId
	 * @param conditionNo
	 * @return
	 */
	public static ScheFuncCond createFromJavaType(String companyId, int conditionNo) {
		
		return new ScheFuncCond(companyId, conditionNo);
	}
}
