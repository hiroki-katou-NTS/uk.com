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
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkSchedulePub;

@Stateless
public class BasicScheduleAdapterImpl implements BasicScheduleAdapter {

	@Inject
	private WorkSchedulePub workSchedulePub;

	@Override
	public Optional<BasicScheduleSidDto> findAllBasicSchedule(String employeeId, GeneralDate baseDate) {
		
		Optional<WorkScheduleExport> basicScheduleExport = workSchedulePub.get(employeeId, baseDate);
		if(!basicScheduleExport.isPresent()) {
			return Optional.empty();
		}
		List<WorkScheduleSidImport> workScheduleSidImports = new ArrayList<>();
		BasicScheduleSidDto basicScheduleSidDto = new BasicScheduleSidDto(basicScheduleExport.get().getEmployeeId(),
				basicScheduleExport.get().getYmd(),
				basicScheduleExport.get().getWorkType(),
				basicScheduleExport.get().getWorkTime(),
				workScheduleSidImports);
		return Optional.of(basicScheduleSidDto);
	}

	@Override
	public List<WorkScheduleSidImport> findAllWorkSchedule() {
		// TODO Auto-generated method stub
		return null;
	}


}
