package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Getter
@Setter
public class OvertimeHolidayWorkActual {
	
	private Optional<AppOverTime> opAppOverTimePre;
	
	private Optional<AppHolidayWork> opAppHolidayWorkPre;
	
	private ActualStatusCheckResult actualStatusCheckResult;
	
	public OvertimeHolidayWorkActual() {
		this.opAppOverTimePre = Optional.empty();
		this.opAppHolidayWorkPre = Optional.empty();
		this.actualStatusCheckResult = null;
	}

}
