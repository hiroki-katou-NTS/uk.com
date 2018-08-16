package nts.uk.screen.at.app.dailyperformance.correction.closure;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ClosureDto;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class FindClosureDateService {

	@Inject
	private ShClosurePub shClosurePub;

	@Inject
	private DailyPerformanceScreenRepo repo;

	public Map<String, DatePeriod> findClosureDate(String companyId, Map<String, String> empMap, GeneralDate baseDate) {
		
		List<ClosureDto> closureDtos = repo.getClosureId(empMap, baseDate);
		
		Map<Integer, DatePeriod> periodExportMap = shClosurePub.findAllPeriod(companyId,
				closureDtos.stream().map(x -> x.getClosureId()).collect(Collectors.toList()), baseDate);
		
		return closureDtos.stream().filter(x -> periodExportMap.containsKey(x.getClosureId()))
				.collect(Collectors.toMap(x -> x.getSid(), x -> periodExportMap.get(x.getClosureId())));
	}
}
