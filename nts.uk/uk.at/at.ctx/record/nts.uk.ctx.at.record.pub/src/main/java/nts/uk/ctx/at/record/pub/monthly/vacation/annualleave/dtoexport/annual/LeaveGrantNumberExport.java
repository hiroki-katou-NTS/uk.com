package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 休暇付与数
 *
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class LeaveGrantNumberExport {

	/**
	 * 日数
	 */
	protected double days;

	/**
	 * 時間
	 */
	protected Optional<Integer> minutes;

	/**
	 * コンストラクタ
	 * @param c
	 */
	public LeaveGrantNumberExport(LeaveGrantNumberExport c) {
		days = c.getDays();
		minutes = c.getMinutes().map(mapper->mapper);
	}

	/**
	 * クローン
	 */
	public LeaveGrantNumberExport clone() {
		return new LeaveGrantNumberExport(this);
	}

}
