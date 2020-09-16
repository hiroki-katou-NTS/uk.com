package nts.uk.screen.at.app.dailyperformance.correction.closure;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ClosureDto;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class FindClosureDateService {

	@Inject
	private ShClosurePub shClosurePub;

	@Inject
	private DailyPerformanceScreenRepo repo;
	
	@Inject
	private ClosureRepository closureRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	public Map<String, DatePeriod> findClosureDate(String companyId, Map<String, String> empMap, GeneralDate baseDate) {
		
		List<ClosureDto> closureDtos = repo.getClosureId(empMap, baseDate);
		
		Map<Integer, DatePeriod> periodExportMap = shClosurePub.findAllPeriod(companyId,
				closureDtos.stream().map(x -> x.getClosureId()).collect(Collectors.toList()), baseDate);
		
		return closureDtos.stream().filter(x -> periodExportMap.containsKey(x.getClosureId()))
				.collect(Collectors.toMap(x -> x.getSid(), x -> periodExportMap.get(x.getClosureId())));
	}
	
	// 指定した年月日時点の社員の締め期間を取得する
	public Optional<ClosurePeriod> getClosurePeriod(String employeeId, GeneralDate baseDate) {
		Closure closure = ClosureService.getClosureDataByEmployee(
				ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
				new CacheCarrier(), employeeId, baseDate);
		if(closure == null) return Optional.empty();
		Optional<ClosurePeriod> closurePeriodOpt = closure.getClosurePeriodByYmd(baseDate);
		return closurePeriodOpt;
	}
}
