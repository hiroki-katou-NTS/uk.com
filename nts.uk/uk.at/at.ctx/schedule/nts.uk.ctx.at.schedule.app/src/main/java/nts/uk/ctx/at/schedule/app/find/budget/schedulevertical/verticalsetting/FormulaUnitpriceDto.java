package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaUnitprice;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormulaUnitpriceDto {
	/** 会社ID **/
	private String companyId;

	/** コード **/
	private String verticalCalCd;
	
	/** 汎用縦計項目ID **/
	private String verticalCalItemId;
	
	/**出勤区分**/
	private int attendanceAtr;
	
	/**単価**/
	private int unitPrice;
	
	/**
	 * fromDomain
	 * @param unitprice
	 * @return
	 */
	public static FormulaUnitpriceDto fromDomain (FormulaUnitprice unitprice){
		return new FormulaUnitpriceDto(
				unitprice.getCompanyId(),
				unitprice.getVerticalCalCd(),
				unitprice.getVerticalCalItemId(),
				unitprice.getAttendanceAtr().value,
				unitprice.getUnitPrice().value
				);
	}
}
