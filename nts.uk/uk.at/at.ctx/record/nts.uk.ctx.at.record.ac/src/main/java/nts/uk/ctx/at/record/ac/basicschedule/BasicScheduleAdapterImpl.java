package nts.uk.ctx.at.record.ac.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.WorkScheduleSidImport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub;

@Stateless
public class BasicScheduleAdapterImpl implements BasicScheduleAdapter {

	@Inject
	private ScBasicSchedulePub scBasicSchedulePub;

	@Override
	public Optional<BasicScheduleSidDto> findAllBasicSchedule(String employeeId, GeneralDate baseDate) {
		Optional<ScBasicScheduleExport> basicScheduleExport = this.scBasicSchedulePub.findById(employeeId, baseDate);
		List<WorkScheduleSidImport> workScheduleSidImports = new ArrayList<>();
		basicScheduleExport.get().getWorkScheduleTimeZones().forEach(item -> {
			WorkScheduleSidImport workScheduleSidImport = new WorkScheduleSidImport(item.getScheduleCnt(), item.getScheduleStartClock(), item.getScheduleEndClock(), item.getBounceAtr());
			workScheduleSidImports.add(workScheduleSidImport);
		});	
		
		BasicScheduleSidDto basicScheduleSidDto = new BasicScheduleSidDto(basicScheduleExport.get().getEmployeeId(),
				basicScheduleExport.get().getDate(),
				basicScheduleExport.get().getWorkTypeCode(),
				basicScheduleExport.get().getWorkTimeCode(),
				basicScheduleExport.get().getWorkDayAtr(),
				workScheduleSidImports);
		return Optional.of(basicScheduleSidDto);
	}

	@Override
	public List<WorkScheduleSidImport> findAllWorkSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

}
