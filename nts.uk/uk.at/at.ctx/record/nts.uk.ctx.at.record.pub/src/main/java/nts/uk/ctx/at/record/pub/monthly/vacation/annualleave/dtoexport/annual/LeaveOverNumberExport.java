package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 休暇上限超過消滅数
 * @author masaaki_jinno
 *
 */
@Data
@AllArgsConstructor
public class LeaveOverNumberExport {

	/**
	 * 日数
	 */
	public double numberOverDays;

	/**
	 * 時間
	 */
	public Optional<Integer> timeOver;

	/**
	 * コンストラクタ
	 * @param c
	 */
	public LeaveOverNumberExport(LeaveOverNumberExport c) {
		numberOverDays = c.getNumberOverDays();
		timeOver = c.getTimeOver().map(mapper->mapper);
	}

	/**
	 * クローン
	 */
	public LeaveOverNumberExport clone() {
		return new LeaveOverNumberExport(this);
	}

}
