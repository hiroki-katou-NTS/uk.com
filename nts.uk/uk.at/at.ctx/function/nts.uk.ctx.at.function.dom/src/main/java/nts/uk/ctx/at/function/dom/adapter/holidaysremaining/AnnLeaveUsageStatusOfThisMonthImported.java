package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.YearMonth;

/*
 * 年休利用当月状況
 */
@Getter
public class AnnLeaveUsageStatusOfThisMonthImported {
	// 年月
	private YearMonth yearMonth;
	// 月度使用数
	private Double monthlyUsageDays;
	// 月度使用時間
	private Optional<Integer> monthlyUsageTime;
	// 月度残日数
	private Double monthlyRemainingDays;
	// 月度残時間
	private Optional<Integer> monthlyRemainingTime;
	
	public AnnLeaveUsageStatusOfThisMonthImported(YearMonth yearMonth, Double monthlyUsageDays, Optional<Integer> monthlyUsageTime,
			Double monthlyRemainingDays, Optional<Integer> monthlyRemainingTime) {
		super();
		this.yearMonth = yearMonth;
		this.monthlyUsageDays = monthlyUsageDays;
		this.monthlyUsageTime = monthlyUsageTime;
		this.monthlyRemainingDays = monthlyRemainingDays;
		this.monthlyRemainingTime = monthlyRemainingTime;
	}
}
