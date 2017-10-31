package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 回数集計NO
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class HoriTotalCNTSet extends AggregateRoot{
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private String categoryCode;
	/** 集計項目NO */
	private int totalItemNo;
	/** 回数集計No **/
	private int totalTimeNo;
	
	public static HoriTotalCNTSet createFromJavaType(String companyId, String categoryCode, int totalItemNo, int totalTimeNo){
		return new HoriTotalCNTSet (companyId, 
									categoryCode, 
									totalItemNo,
									totalTimeNo);
	}
}
