package nts.uk.ctx.at.shared.dom.vacation.algorithm;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author ThanhNX
 *
 *         逐次発生の休暇設定（List）
 */
@Getter
public class TimeLapseVacationSetting {

	// 期間
	private DatePeriod period;

	// 管理区分
	private boolean managerCate;

	// 使用期限
	private int expiryDate;

	// 先取り許可
	private boolean receivAdvance;

	// 時間管理区分
	private Optional<Boolean> managerTimeCate;

	// 時間消化単位
	private Optional<Integer> timeDigestionUnit;

	public TimeLapseVacationSetting(DatePeriod period, boolean managerCate, int expiryDate, boolean receivAdvance,
			Optional<Boolean> managerTimeCate, Optional<Integer> timeDigestionUnit) {
		this.period = period;
		this.managerCate = managerCate;
		this.expiryDate = expiryDate;
		this.receivAdvance = receivAdvance;
		this.managerTimeCate = managerTimeCate;
		this.timeDigestionUnit = timeDigestionUnit;
	}

}
