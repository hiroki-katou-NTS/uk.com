package nts.uk.ctx.at.record.app.find.dailyperform.workinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class WorkInformationOfDailyFinder extends FinderFacade {

	@Inject
	private WorkInformationRepository workInfoRepo;

	@Override
	@SuppressWarnings("unchecked")
	public WorkInformationOfDailyDto find(String employeeId, GeneralDate baseDate) {
		return WorkInformationOfDailyDto.getDto(this.workInfoRepo.find(employeeId, baseDate).orElse(null));
	}

}
