package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class TotalEvalOrderCommand {
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private String categoryCode;
	/** 集計項目NO */
	private Integer totalItemNo;
	/** 並び順 */
	private Integer dispOrder;
	
	/**
	 * convert total eval order to domain
	 * @param companyId
	 * @param categoryCode
	 * @return
	 */
	public TotalEvalOrder toDomainOrder(String companyId, String categoryCode){
		return TotalEvalOrder.createFromJavaType(companyId, categoryCode, totalItemNo, dispOrder);
	}
}
