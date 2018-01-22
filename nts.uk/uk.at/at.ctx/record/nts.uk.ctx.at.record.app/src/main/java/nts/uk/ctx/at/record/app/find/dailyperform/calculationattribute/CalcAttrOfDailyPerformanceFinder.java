package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class CalcAttrOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private CalAttrOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public CalcAttrOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		return CalcAttrOfDailyPerformanceDto.getDto(this.repo.find(employeeId, baseDate));
	}
}
