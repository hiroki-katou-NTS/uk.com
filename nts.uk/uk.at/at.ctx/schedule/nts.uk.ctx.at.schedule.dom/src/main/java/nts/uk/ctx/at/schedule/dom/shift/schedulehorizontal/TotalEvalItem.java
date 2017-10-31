package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.TotalItemName;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.TotalItemNo;
/**
 * 横計集計項目
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class TotalEvalItem extends DomainObject{
	/**会社ID**/
	private String companyId;
	/** 集計項目NO */
	private TotalItemNo totalItemNo;
	/** 集計項目名称 **/
	private TotalItemName totalItemName;
	
	public static TotalEvalItem createFromJavaType(String companyId, Integer totalItemNo, String totalItemName){
		return new TotalEvalItem(companyId, new TotalItemNo(totalItemNo), new TotalItemName(totalItemName));
	}
}
