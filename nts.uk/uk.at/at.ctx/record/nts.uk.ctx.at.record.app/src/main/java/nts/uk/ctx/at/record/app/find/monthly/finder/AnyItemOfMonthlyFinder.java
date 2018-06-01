package nts.uk.ctx.at.record.app.find.monthly.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AnyItemOfMonthlyDto;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AnyItemOfMonthlyFinder extends MonthlyFinderFacade {
	
	@Inject
	private OptionalItemRepository optionalMasterRepo;
	
	@Inject
	private AnyItemOfMonthlyRepository repo;

	@Override
	@SuppressWarnings("unchecked")
	public AnyItemOfMonthlyDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		List<AnyItemOfMonthly> domain = this.repo.findByMonthlyAndClosure(employeeId, yearMonth, closureId, closureDate);
		Map<Integer, OptionalItem> optionalMaster = getMaster(domain);
		return AnyItemOfMonthlyDto.from(domain, optionalMaster);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, DatePeriod range) {
		return (List<T>) ConvertHelper.yearMonthsBetween(range).stream().map(ym -> find(employeeId, ym))
				.flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonth yearMonth) {
		List<AnyItemOfMonthly> domain = this.repo.findBySidsAndMonths(new ArrayList<>(employeeId), Arrays.asList(yearMonth));
		Map<Integer, OptionalItem> optionalMaster = getMaster(domain);
		return (List<T>) domain.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId() + c.getYearMonth(), 
				Collectors.collectingAndThen(Collectors.toList(), list -> AnyItemOfMonthlyDto.from(list, optionalMaster))))
				.entrySet().stream().map(c -> c.getValue()).collect(Collectors.toList());
		
	}

	private Map<Integer, OptionalItem> getMaster(List<AnyItemOfMonthly> domain) {
		Map<Integer, OptionalItem> optionalMaster = null;
		if (!domain.isEmpty()) {
			List<Integer> itemIds = domain.stream().map(i -> i.getAnyItemId()).collect(Collectors.toList());
			if(!itemIds.isEmpty()){
				optionalMaster = optionalMasterRepo
						.findByListNos(AppContexts.user().companyId(), itemIds).stream()
						.collect(Collectors.toMap(c -> c.getOptionalItemNo().v(), c -> c));
			}
		}
		return optionalMaster;
	}
}
