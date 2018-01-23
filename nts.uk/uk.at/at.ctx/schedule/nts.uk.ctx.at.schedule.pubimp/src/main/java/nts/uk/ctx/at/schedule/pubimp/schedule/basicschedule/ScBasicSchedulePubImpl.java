/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pubimp.schedule.basicschedule;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.WorkScheduleTimeZoneExport;

/**
 * The Class ScBasicSchedulePubImpl.
 */
@Stateless
public class ScBasicSchedulePubImpl implements ScBasicSchedulePub{
	
	/** The repository. */
	@Inject
	private BasicScheduleRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub#
	 * findById(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<ScBasicScheduleExport> findById(String employeeId, GeneralDate baseDate) {
		return this.repository.find(employeeId, baseDate).map(domain -> this.convertExport(domain));
	}
	
	/**
	 * Convert export.
	 *
	 * @param domain the domain
	 * @return the sc basic schedule export
	 */
	private ScBasicScheduleExport convertExport(BasicSchedule domain){
		ScBasicScheduleExport export = new ScBasicScheduleExport();
		export.setDate(domain.getDate());
		export.setEmployeeId(domain.getEmployeeId());
		export.setWorkScheduleTimeZones(domain.getWorkScheduleTimeZones().stream()
				.map(timezone -> this.convertTimeZoneExport(timezone)).collect(Collectors.toList()));
		export.setWorkTimeCode(domain.getWorkTimeCode());
		export.setWorkTypeCode(domain.getWorkTypeCode());
		return export;
	}
	
	/**
	 * Convert time zone export.
	 *
	 * @param timezone the timezone
	 * @return the work schedule time zone export
	 */
	private WorkScheduleTimeZoneExport convertTimeZoneExport(WorkScheduleTimeZone timezone){
		WorkScheduleTimeZoneExport export = new WorkScheduleTimeZoneExport();
		export.setBounceAtr(timezone.getBounceAtr().value);
		export.setScheduleCnt(timezone.getScheduleCnt());
		export.setScheduleStartClock(timezone.getScheduleStartClock().valueAsMinutes());
		export.setScheduleEndClock(timezone.getScheduleEndClock().valueAsMinutes());
		return export;
	}

}
