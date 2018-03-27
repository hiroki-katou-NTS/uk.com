package nts.uk.ctx.at.record.app.find.monthly.vtotalmethod;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author HoangNDH
 *
 */
@Getter
@Setter
public class VerticalTotalMethodOfMonthlyDto {
	/** 振出日数 */
	private int attendanceItemCountingMethod;
	
	/** 計算対象外のカウント条件 */ 
	private int specCountNotCalcSubject;
	
	/** 勤務種類のカウント条件 */
	private Map<Integer, Boolean> workTypeSetting;
}
