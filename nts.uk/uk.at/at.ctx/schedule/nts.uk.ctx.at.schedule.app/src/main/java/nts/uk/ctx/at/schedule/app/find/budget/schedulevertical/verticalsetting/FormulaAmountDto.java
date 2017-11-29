package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaAmount;

/**
 * TanLV
 *
 */
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

	/**
	 * fromDomain
	 * 
	 * @param domain
	 * @return
	 */
	public static FormulaAmountDto fromDomain(FormulaAmount domain) {
			FormulaMoneyDto money = FormulaMoneyDto.fromDomain(domain.getMoneyFunc());
			FormulaTimeUnitDto time = FormulaTimeUnitDto.fromDomain(domain.getTimeUnit());

		return new FormulaAmountDto(domain.getCompanyId(), domain.getVerticalCalCd(), domain.getVerticalCalItemId(),
				domain.getCalMethodAtr().value, money, time);
	}

}
