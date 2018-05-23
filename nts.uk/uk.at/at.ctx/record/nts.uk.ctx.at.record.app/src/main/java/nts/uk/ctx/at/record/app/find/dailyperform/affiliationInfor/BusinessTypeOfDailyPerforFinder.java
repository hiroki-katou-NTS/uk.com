package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.BusinessTypeOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.WorkTypeOfDailyPerforRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/** 日別実績の所属情報 Finder */
@Stateless
public class BusinessTypeOfDailyPerforFinder extends FinderFacade {

	@Inject
	private WorkTypeOfDailyPerforRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public BusinessTypeOfDailyPerforDto find(String employeeId, GeneralDate baseDate) {
		return BusinessTypeOfDailyPerforDto
				.getDto(this.repo.findByKey(employeeId, baseDate).orElse(null));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConvertibleAttendanceItem> List<T> find(List<String> employeeId, DatePeriod baseDate) {
		return (List<T>) this.repo.finds(employeeId, baseDate).stream()
				.map(c -> BusinessTypeOfDailyPerforDto.getDto(c)).collect(Collectors.toList());
	}
}
