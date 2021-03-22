package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 休暇数情報（明細）
 * 
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public class LeaveNumberInfoExport implements Cloneable {

	/**
	 * 付与数
	 */
	protected LeaveGrantNumberExport grantNumber;

	/**
	 * 使用数
	 */
	@Setter
	protected LeaveUsedNumberExport usedNumber;

	/**
	 * 残数
	 */
	protected LeaveRemainingNumberExport remainingNumber;

	/**
	 * 使用率
	 */
	protected BigDecimal usedPercent;

}
