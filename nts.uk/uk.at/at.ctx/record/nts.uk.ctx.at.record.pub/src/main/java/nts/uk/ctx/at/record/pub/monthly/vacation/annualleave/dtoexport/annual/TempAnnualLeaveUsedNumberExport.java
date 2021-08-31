package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 暫定データの年休使用数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class TempAnnualLeaveUsedNumberExport {

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
	public TempAnnualLeaveUsedNumberExport(TempAnnualLeaveUsedNumberExport c) {
		days = c.getDays();
		minutes = c.getMinutes().map(mapper->mapper);
	}

	/**
	 * クローン
	 */
	public TempAnnualLeaveUsedNumberExport clone() {
		return new TempAnnualLeaveUsedNumberExport(this);
	}

}
