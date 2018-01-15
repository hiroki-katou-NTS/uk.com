package nts.uk.ctx.at.request.ac.schedule.schedule.basicschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.WorkScheduleTimeZoneImport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ScBasicScheduleAdapterImpl implements ScBasicScheduleAdapter {
	
	@Inject
	private ScBasicSchedulePub scBasicSchedulePub;
	
	@Override
	public Optional<ScBasicScheduleImport> findByID(String employeeID, GeneralDate date) {
		return scBasicSchedulePub.findById(employeeID, date)
				.map(x -> {
					WorkScheduleTimeZoneImport workScheduleTimeZoneImport1 = x.getWorkScheduleTimeZones().stream()
						.filter(y -> y.getScheduleCnt()==1).findAny()
						.map(z -> new WorkScheduleTimeZoneImport(z.getScheduleStartClock(), z.getScheduleEndClock()))
						.orElse(new WorkScheduleTimeZoneImport(-1, -1));
					WorkScheduleTimeZoneImport workScheduleTimeZoneImport2 = x.getWorkScheduleTimeZones().stream()
							.filter(y -> y.getScheduleCnt()==2).findAny()
							.map(z -> new WorkScheduleTimeZoneImport(z.getScheduleStartClock(), z.getScheduleEndClock()))
							.orElse(new WorkScheduleTimeZoneImport(-1, -1));
					return new ScBasicScheduleImport(
							employeeID, 
							date, 
							x.getWorkTypeCode(), 
							x.getWorkTimeCode(), 
							workScheduleTimeZoneImport1.getScheduleStartClock(), 
							workScheduleTimeZoneImport1.getScheduleEndClock(), 
							workScheduleTimeZoneImport2.getScheduleStartClock(), 
							workScheduleTimeZoneImport2.getScheduleEndClock());
				});
	}

}
