package nts.uk.ctx.at.schedule.dom.shift.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.TotalItemName;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.TotalItemNo;
/**
 * 
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class TotalEvalItem {
	/**会社ID**/
	private String companyId;
	/** 集計項目NO */
	private TotalItemNo totalItemNo;
	/** カテゴリ名称 */
	private TotalItemName totalItemName;
	
	public static TotalEvalItem createFromJavaType(String companyId, Integer totalItemNo, String totalItemName){
		return new TotalEvalItem(companyId, new TotalItemNo(totalItemNo), new TotalItemName(totalItemName));
	}
}
