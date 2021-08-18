package nts.uk.ctx.at.request.dom.application.holidayworktime.commonalgorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkHolidayInfo {
	/**
	 * 初期選択勤務種類
	 */
	private Optional<WorkTypeCode> initWorkTypeCd = Optional.empty();
	/**
	 * 初期選択就業時間帯
	 */
	private Optional<WorkTimeCode> initWorkTimeCd = Optional.empty();
}
