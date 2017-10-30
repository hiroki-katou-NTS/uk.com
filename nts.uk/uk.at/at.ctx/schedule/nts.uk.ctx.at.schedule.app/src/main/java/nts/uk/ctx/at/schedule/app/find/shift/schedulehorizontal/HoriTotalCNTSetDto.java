package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoriTotalCNTSetDto {
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private String categoryCode;
	/** 集計項目NO */
	private int totalItemNo;
	/** 回数集計No **/
	private int totalTimeNo;
}
