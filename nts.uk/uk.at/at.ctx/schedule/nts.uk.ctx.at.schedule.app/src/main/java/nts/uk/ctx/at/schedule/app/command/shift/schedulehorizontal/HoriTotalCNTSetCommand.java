package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCNTSet;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class HoriTotalCNTSetCommand {
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private String categoryCode;
	/** 集計項目NO */
	private int totalItemNo;
	/** 回数集計No **/
	private int totalTimeNo;
	
	/**
	 * convert hori total cnt set to domain
	 * @param companyId
	 * @param categoryCode
	 * @param totalTimeNo
	 * @return
	 */
	public HoriTotalCNTSet toDomainCNTSet(String companyId, String categoryCode, int totalItemNo, int totalTimeNo){
		return HoriTotalCNTSet.createFromJavaType(companyId, categoryCode, totalItemNo, totalTimeNo);
	}
}
