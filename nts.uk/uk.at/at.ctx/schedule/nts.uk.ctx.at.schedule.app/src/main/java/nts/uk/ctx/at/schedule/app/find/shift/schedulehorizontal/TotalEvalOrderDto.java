package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalEvalOrderDto {
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private String categoryCode;
	/** 集計項目NO */
	private Integer totalItemNo;
	/** 並び順 */
	private Integer dispOrder;
	private HoriCalDaySetDto horiCalDaySetDto;
}
