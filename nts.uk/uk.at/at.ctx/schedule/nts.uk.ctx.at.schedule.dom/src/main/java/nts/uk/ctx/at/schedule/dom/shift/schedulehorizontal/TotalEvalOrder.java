package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.CategoryCode;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.TotalItemNo;
/**
 * 集計項目
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class TotalEvalOrder extends DomainObject{
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private CategoryCode categoryCode;
	/** 集計項目NO */
	private TotalItemNo totalItemNo;
	/** 並び順 */
	private Integer dispOrder;
	private HoriCalDaysSet horiCalDaysSet;
	private List<HoriTotalCNTSet> cntSetls;
	
	public static TotalEvalOrder createFromJavaType(String companyId, 
													String categoryCode, 
													int totalItemNo, 
													Integer dispOrder, 
													HoriCalDaysSet horiCalDaysSet, 
													List<HoriTotalCNTSet> cntSetls){
		return new TotalEvalOrder(companyId, new CategoryCode(categoryCode), 
										new TotalItemNo(totalItemNo),
										dispOrder, horiCalDaysSet, cntSetls);
	}
}
