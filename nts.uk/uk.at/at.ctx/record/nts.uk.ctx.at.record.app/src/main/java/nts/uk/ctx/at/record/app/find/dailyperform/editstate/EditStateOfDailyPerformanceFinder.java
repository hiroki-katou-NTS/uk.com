package nts.uk.ctx.at.record.app.find.dailyperform.editstate;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class EditStateOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private EditStateOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public EditStateOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		EditStateOfDailyPerformanceDto result = new EditStateOfDailyPerformanceDto();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EditStateOfDailyPerformanceDto> finds(String employeeId, GeneralDate baseDate) {
		return ConvertHelper.mapTo(this.repo.findByKey(employeeId, baseDate),
				(c) -> EditStateOfDailyPerformanceDto.getDto(c));
	}

}
