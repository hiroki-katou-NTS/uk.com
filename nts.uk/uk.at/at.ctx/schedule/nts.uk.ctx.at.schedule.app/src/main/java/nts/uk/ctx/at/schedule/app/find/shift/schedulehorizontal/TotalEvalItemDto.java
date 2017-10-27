package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalEvalItemDto {
	/**会社ID**/
	private String companyId;
	/** 集計項目NO */
	private int totalItemNo;
	/** 集計項目名称 **/
	private String totalItemName;
}
