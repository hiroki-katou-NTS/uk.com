package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
public class DailyEventCommonCommand {

	public boolean isTriggerRelatedEvent = true;

	/** 年月日: 年月日 */
	public String employeeId;

	/** 社員ID: 社員ID */
	public GeneralDate targetDate;

	public Optional<String> companyId = Optional.empty();

	public Optional<WorkInfoOfDailyPerformance> cachedWorkInfo = Optional.empty();

	public Optional<WorkType> cachedWorkType = Optional.empty();

	public Optional<TimeLeavingOfDailyPerformance> cachedTimeLeave = Optional.empty();

	public Optional<List<EditStateOfDailyPerformance>> cachedEditState = Optional.empty();

	public boolean actionOnCache = false;

	public DailyEventCommonCommand employeeId(String employeeId) {
		this.employeeId = employeeId;
		return this;
	}
	
	public DailyEventCommonCommand isTriggerRelatedEvent(boolean isTriggerRelatedEvent) {
		this.isTriggerRelatedEvent = isTriggerRelatedEvent;
		return this;
	}
	
	public DailyEventCommonCommand targetDate(GeneralDate targetDate) {
		this.targetDate = targetDate;
		return this;
	}
	
	public DailyEventCommonCommand companyId(String companyId) {
		this.companyId = Optional.ofNullable(companyId);
		return this;
	}
	
	public DailyEventCommonCommand cachedWorkInfo(WorkInfoOfDailyPerformance cachedWorkInfo) {
		this.cachedWorkInfo = Optional.ofNullable(cachedWorkInfo);
		return this;
	}
	
	public DailyEventCommonCommand cachedWorkType(WorkType cachedWorkType) {
		this.cachedWorkType = Optional.ofNullable(cachedWorkType);
		return this;
	}
	
	public DailyEventCommonCommand cachedTimeLeave(TimeLeavingOfDailyPerformance cachedTimeLeave) {
		this.cachedTimeLeave = Optional.ofNullable(cachedTimeLeave);
		return this;
	}
	
	public DailyEventCommonCommand cachedEditState(List<EditStateOfDailyPerformance> cachedEditState) {
		this.cachedEditState = Optional.of(cachedEditState);
		return this;
	}
	
	public DailyEventCommonCommand actionOnCache(boolean actionOnCache) {
		this.actionOnCache = actionOnCache;
		return this;
	}
	
	public DailyEventCommonCommand build() {
		return this;
	}

}
