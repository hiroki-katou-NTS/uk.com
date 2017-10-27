package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.CategoryCode;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.TotalItemNo;
/**
 * 
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class HoriTotalCNTSet extends AggregateRoot{
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private CategoryCode categoryCode;
	/** 集計項目NO */
	private TotalItemNo totalItemNo;
	
	public static HoriTotalCNTSet createFromJavaType(String companyId, String categoryCode, Integer totalItemNo){
		return new HoriTotalCNTSet (companyId, 
									new CategoryCode(categoryCode), 
									new TotalItemNo(totalItemNo));
	}
}
