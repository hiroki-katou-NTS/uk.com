package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyEventCommonCommand;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Getter
public class TimeLeaveUpdateByWorkInfoChangeCommand extends DailyEventCommonCommand {
	
	/** 新しい勤務種類コード: 勤務種類コード */
	WorkTypeCode newWorkTypeCode;

	/** 新しい就業時間帯コード: 就業時間帯コード */
	Optional<WorkTimeCode> newWorkTimeCode = Optional.empty();

	Optional<WorkingConditionItem> cachedWorkCondition = Optional.empty();
	
	public static TimeLeaveUpdateByWorkInfoChangeCommand builder() {
		return new TimeLeaveUpdateByWorkInfoChangeCommand();
	}
	public TimeLeaveUpdateByWorkInfoChangeCommand newWorkTypeCode(WorkTypeCode newWorkTypeCode) {
		this.newWorkTypeCode = newWorkTypeCode;
		return this;
	}
	
	public TimeLeaveUpdateByWorkInfoChangeCommand newWorkTimeCode(WorkTimeCode newWorkTimeCode) {
		this.newWorkTimeCode = Optional.ofNullable(newWorkTimeCode);
		return this;
	}
	
	public TimeLeaveUpdateByWorkInfoChangeCommand cachedWorkCondition(WorkingConditionItem cachedWorkCondition) {
		this.cachedWorkCondition = Optional.of(cachedWorkCondition);
		return this;
	}
}
