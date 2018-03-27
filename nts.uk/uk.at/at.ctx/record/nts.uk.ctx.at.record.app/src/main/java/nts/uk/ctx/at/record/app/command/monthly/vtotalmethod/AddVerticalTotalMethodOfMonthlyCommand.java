package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

import java.util.Map;

import lombok.Data;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly;

/**
 * The Class AddVerticalTotalMethodOfMonthlyCommand.
 *
 * @author HoangNDH
 */
@Data
public class AddVerticalTotalMethodOfMonthlyCommand {
	
	/** 振出日数 */
	private int attendanceItemCountingMethod;
	
//	/** 計算対象外のカウント条件 */ 
//	private int specCountNotCalcSubject;
//	
//	/** 勤務種類のカウント条件 */
//	private Map<Integer, Boolean> workTypeSetting;
	
	public VerticalTotalMethodOfMonthly toDomain(String companyId) {
		return VerticalTotalMethodOfMonthly.createFromJavaType(companyId, attendanceItemCountingMethod);
	}
}
