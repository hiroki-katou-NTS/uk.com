package nts.uk.ctx.at.request.ac.schedule.schedule.basicschedule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.WorkScheduleTimeZoneImport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ScBasicScheduleAdapterImpl implements ScBasicScheduleAdapter {
	
	@Inject
	private ScBasicSchedulePub scBasicSchedulePub;
	
	@Override
	public Optional<ScBasicScheduleImport> findByID(String employeeID, GeneralDate date) {
		return scBasicSchedulePub.findById(employeeID, date)
				.map(x -> convertTo(x));
	}

	private ScBasicScheduleImport convertTo(ScBasicScheduleExport x) {
		return new ScBasicScheduleImport(
				x.getEmployeeId(), 
				x.getDate(), 
				x.getWorkTypeCode(), 
				x.getWorkTimeCode(), 
				x.getWorkScheduleTimeZones().stream().map(y -> new WorkScheduleTimeZoneImport(
						y.getScheduleCnt(), 
						y.getScheduleStartClock(), 
						y.getScheduleEndClock(), 
						y.getBounceAtr()))
				.collect(Collectors.toList()));
	}

	@Override
	public List<ScBasicScheduleImport> findByID(List<String> employeeID, DatePeriod date) {
		
		return scBasicSchedulePub.findById(employeeID, date).stream().map(x -> convertTo(x)).collect(Collectors.toList());
	}

}
