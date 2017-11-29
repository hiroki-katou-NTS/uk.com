package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * 金額計算式
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class FormulaAmount {
	/** 会社ID */
	private String companyId;

	/** コード */
	private String verticalCalCd;

	/** 計算式 */
	private String verticalCalItemId;

	private CalMethodAtr calMethodAtr;
	
	private FormulaMoney moneyFunc;
	
	private FormulaTimeUnit timeUnit;

	public static FormulaAmount createFromJavatype(String companyId, String verticalCalCd, String verticalCalItemId,
			int calMethodAtr,  FormulaMoney moneyFunc,FormulaTimeUnit timeUnit) {
		return new FormulaAmount(companyId, verticalCalCd, verticalCalItemId,
				EnumAdaptor.valueOf(calMethodAtr, CalMethodAtr.class), moneyFunc, timeUnit);
	}
}
