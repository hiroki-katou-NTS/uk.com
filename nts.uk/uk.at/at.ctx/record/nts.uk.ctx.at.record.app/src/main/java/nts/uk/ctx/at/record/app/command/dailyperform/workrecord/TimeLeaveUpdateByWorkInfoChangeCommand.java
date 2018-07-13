package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Builder
@Data
public class TimeLeaveUpdateByWorkInfoChangeCommand {
	
	@Builder.Default
	boolean isTriggerRelatedEvent = true;

	/** 年月日: 年月日 */
	String employeeId;

	/** 社員ID: 社員ID */
	GeneralDate targetDate;
	
	@Builder.Default
	Optional<String> companyId = Optional.empty();
	
	/** 新しい勤務種類コード: 勤務種類コード */
	WorkTypeCode newWorkTypeCode;

	/** 新しい就業時間帯コード: 就業時間帯コード */
	@Builder.Default
	Optional<WorkTimeCode> newWorkTimeCode = Optional.empty();

	@Builder.Default
	Optional<WorkInfoOfDailyPerformance> cachedWorkInfo = Optional.empty();

	@Builder.Default
	Optional<WorkType> cachedWorkType = Optional.empty();

	@Builder.Default
	Optional<TimeLeavingOfDailyPerformance> cachedTimeLeave  = Optional.empty();

	@Builder.Default
	Optional<List<EditStateOfDailyPerformance>> cachedEditState = Optional.empty();

	@Builder.Default
	Optional<WorkingConditionItem> cachedWorkCondition = Optional.empty();
	
	@Builder.Default
	boolean actionOnCache = false;
}
