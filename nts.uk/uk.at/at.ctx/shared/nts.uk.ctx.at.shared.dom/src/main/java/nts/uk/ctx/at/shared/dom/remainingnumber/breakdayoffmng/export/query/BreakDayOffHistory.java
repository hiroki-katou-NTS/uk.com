package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BreakDayOffHistory {
	/**
	 * 発生消化年月日
	 */
	private GeneralDate hisDate;
	/**
	 * 休出履歴
	 */
	private Optional<BreakHistoryData> breakHis;
	/**
	 * 代休履歴
	 */
	private Optional<DayOffHistoryData> dayOffHis;
	/**
	 * 使用日数
	 */
	private Double useDays;
}
