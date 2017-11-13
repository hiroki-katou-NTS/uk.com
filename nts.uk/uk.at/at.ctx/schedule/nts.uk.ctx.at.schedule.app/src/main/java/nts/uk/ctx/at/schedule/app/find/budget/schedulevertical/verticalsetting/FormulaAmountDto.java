package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormulaAmountDto {
	/** 会社ID */
	private String companyId;

	/** コード */
	private String verticalCalCd;

	/** 計算式 */
	private String verticalCalItemId;

	/** 計算方法区分 */
	private int calMethodAtr;
	
	private FormulaMoneyDto moneyFunc;
	
	private FormulaTimeUnitDto timeUnit;
	
}
