package nts.uk.ctx.at.record.app.find.dailyperform.remark;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.remark.dto.RemarksOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerformRepo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
/** 日別実績の備考 Finder */
public class RemarksOfDailyFinder extends FinderFacade {

	@Inject
	private RemarksOfDailyPerformRepo repo;

	@SuppressWarnings("unchecked")
	@Override
	public RemarksOfDailyDto find(String employeeId, GeneralDate baseDate) {
		List<RemarksOfDailyPerform> domains = this.repo.getRemarks(employeeId, baseDate);
		if (!domains.isEmpty()) {
			return RemarksOfDailyDto.getDto(domains);
		}
		return new RemarksOfDailyDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConvertibleAttendanceItem> List<T> find(List<String> employeeId, DatePeriod baseDate) {

		return (List<T>) group(this.repo.getRemarks(employeeId, baseDate));
	}

	private List<RemarksOfDailyDto> group(List<RemarksOfDailyPerform> domains) {
		Map<String, Map<GeneralDate, List<RemarksOfDailyPerform>>> remarks = domains.stream()
				.collect(Collectors.groupingBy(RemarksOfDailyPerform::getEmployeeId,
						Collectors.collectingAndThen(Collectors.toList(), list -> list.stream()
								.collect(Collectors.groupingBy(RemarksOfDailyPerform::getYmd, Collectors.toList())))));

		List<RemarksOfDailyDto> dto = new ArrayList<>();

		remarks.entrySet().forEach(es -> {
			es.getValue().entrySet().forEach(ves -> {
				dto.add(RemarksOfDailyDto.getDto(ves.getValue()));
			});
		});

		return dto;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Map<String, List<GeneralDate>> param) {
		return (List<T>) group(this.repo.getRemarks(param));
	}

	@Override
	public Object getDomain(String employeeId, GeneralDate baseDate) {
		return repo.getRemarks(employeeId, baseDate);
	}
}
