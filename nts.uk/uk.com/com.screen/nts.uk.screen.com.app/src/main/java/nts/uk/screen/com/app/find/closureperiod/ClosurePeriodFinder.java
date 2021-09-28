package nts.uk.screen.com.app.find.closureperiod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosurePeriodDto;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ClosurePeriodFinder {
	
	@Inject
	private ClosureRepository closureRepo;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	/**
	 * 指定した年月日時点の社員の締め期間を取得する
	 * @param employeeId	社員ID
	 * @param baseDate		年月日
	 * @return	
	 */
	public Optional<ClosurePeriodDto> getClosurePeriod(String employeeId, GeneralDate baseDate) {
		// 社員に対応する処理締めを取得する
		Closure closure = ClosureService.getClosureDataByEmployee(
				ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
				new CacheCarrier(), employeeId, baseDate);
		// 指定した年月日時点の締め期間を取得する
		return Optional.ofNullable(closure).map(data -> data.getClosurePeriodByYmd(baseDate).orElse(null))
				.map(data -> ClosurePeriodDto.builder()
						.startDate(data.getPeriod().start()).endDate(data.getPeriod().end()).build());
	}
}
