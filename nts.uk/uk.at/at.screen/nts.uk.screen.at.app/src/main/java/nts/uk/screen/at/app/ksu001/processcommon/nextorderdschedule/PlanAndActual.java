package nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanAndActual {

	private Map<ScheManaStatuTempo, Optional<WorkSchedule>> schedule = new HashMap<ScheManaStatuTempo, Optional<WorkSchedule>>();
	
	private Map<ScheManaStatuTempo , Optional<IntegrationOfDaily>> dailySchedule = new HashMap<ScheManaStatuTempo, Optional<IntegrationOfDaily>>();
}
