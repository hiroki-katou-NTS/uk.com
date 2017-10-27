package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.CategoryCode;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.CategoryName;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.Memo;
/**
 * 
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class HoriTotalCategory {
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private CategoryCode categoryCode;
	/** カテゴリ名称 */
	private CategoryName categoryName;
	/** メモ */
	private Memo memo;
	private List<TotalEvalOrder> totalEvalOrders;
	
	public static HoriTotalCategory createFromJavaType(String companyId, String categoryCode, 
														String categoryName, String memo, 
														List<TotalEvalOrder> totalEvalOrders){
		return new HoriTotalCategory(companyId, new CategoryCode(categoryCode), 
									new CategoryName(categoryName), 
									new Memo(memo), 
									totalEvalOrders);
	} 
}
