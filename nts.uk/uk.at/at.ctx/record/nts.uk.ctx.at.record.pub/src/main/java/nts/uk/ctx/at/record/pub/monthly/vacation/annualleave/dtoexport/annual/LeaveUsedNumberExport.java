package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 休暇使用数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class LeaveUsedNumberExport{

	/**
	 * 日数
	 */
	protected double days;

	/**
	 * 時間
	 */
	protected Optional<Integer> minutes;

	/**
	 * 積み崩し日数
	 */
	protected Optional<Double> stowageDays;

	/**
	 * 上限超過消滅日数
	 */
	public Optional<LeaveOverNumberExport> leaveOverLimitNumber;

}
