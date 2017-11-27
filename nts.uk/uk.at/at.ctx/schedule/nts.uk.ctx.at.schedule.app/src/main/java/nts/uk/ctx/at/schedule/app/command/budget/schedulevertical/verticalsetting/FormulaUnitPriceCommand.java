package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaUnitprice;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaUnitPriceCommand {
	/** 会社ID **/
	private String companyId;

	/** コード **/
	private String verticalCalCd;

	/** 汎用縦計項目ID **/
	private String verticalCalItemId;

	/** 出勤区分 **/
	private int attendanceAtr;

	/** 単価 **/
	private int unitPrice;

	/**
	 * toDomainUnitPrice
	 * @param companyId
	 * @param verticalCalCd
	 * @param verticalCalItemId
	 * @return
	 */
	public FormulaUnitprice toDomainUnitPrice(String companyId, String verticalCalCd, String verticalCalItemId) {
		return FormulaUnitprice.createFromJavatype(companyId, verticalCalCd, verticalCalItemId, attendanceAtr,
				unitPrice);
	}
}
