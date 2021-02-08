package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InitWorkTypeWorkTime{
	
	/**
	 * 初期選択勤務種類
	 */
	private Optional<WorkTypeCode> initWorkTypeCd = Optional.empty();
	/**
	 * 初期選択就業時間帯
	 */
	private Optional<WorkTimeCode> initWorkTimeCd = Optional.empty();
	/**
	 * 初期選択勤務種類名称
	 */
	private Optional<WorkType> initWorkType = Optional.empty();
	/**
	 * 初期選択就業時間帯名称
	 */
	private Optional<WorkTimeSetting> initWorkTime = Optional.empty();
}