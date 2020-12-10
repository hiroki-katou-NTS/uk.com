/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.workrule.closure;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;
import nts.uk.ctx.at.shared.pub.workrule.closure.ClosureDateExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ShortWorkTimePubImpl.
 */
@Stateless
public class ShClosurePubImpl implements ShClosurePub {

	/** The work time hist repo. */
	@Inject
	private ClosureRepository closureRepo;
	
	@Inject
	private GetYearProcessAndPeriod getYearProcessAndPeriod;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub#find(java.lang.
	 * String, int)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<PresentClosingPeriodExport> find(String cId, int closureId) {
		//refactor chuyển lên dom phần nội dung hàm cho đúng theo thiết kế
		//theo điều tra thì đây có vẻ là dành cho 処理年月と締め期間を取得する
		//UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業締め日.アルゴリズム.Query.処理年月と締め期間を取得する.処理年月と締め期間を取得する
		GetYearProcessAndPeriodDto dto = getYearProcessAndPeriod.find(cId, closureId).get();

		// Return
		return Optional.of(PresentClosingPeriodExport.builder()
										.processingYm(dto.getProcessingYm())
										.closureStartDate(dto.getClosureStartDate())
										.closureEndDate(dto.getClosureEndDate())
										.build());
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<PresentClosingPeriodExport> find(String cId, int closureId, GeneralDate date) {
		val cacheCarrier = new CacheCarrier();
		return findRequire(cacheCarrier, cId, closureId, date);
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<PresentClosingPeriodExport> findRequire(CacheCarrier cacheCarrier, String cId, int closureId, GeneralDate date) {
		
		val require = new RequireImpl(cacheCarrier);
		
		Optional<Closure> optClosure = require.findById(closureId);

		// Check exist and active
		if (!optClosure.isPresent() || optClosure.get().getUseClassification()
				.equals(UseClassification.UseClass_NotUse)) {
			return Optional.empty();
		}

		Closure closure = optClosure.get();
		Optional<ClosurePeriod> cPeriod = closure.getClosurePeriodByYmd(date);
		if(cPeriod.isPresent()){
			return Optional.of(PresentClosingPeriodExport.builder().processingYm(cPeriod.get().getYearMonth())
				.closureStartDate(cPeriod.get().getPeriod().start()).closureEndDate(cPeriod.get().getPeriod().end())
				.closureDate(new ClosureDateExport(cPeriod.get().getClosureDate().getClosureDay().v(), cPeriod.get().getClosureDate().getLastDayOfMonth()))
				.build());
		}else{
			return Optional.empty();
		}
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Map<Integer, DatePeriod> findAllPeriod(String cId, List<Integer> closureId, GeneralDate date) {
		Map<Integer, DatePeriod> resultExport = new HashMap<>();
		List<Closure> optClosures = closureRepo.findByListId(cId, closureId);
		optClosures = optClosures.stream()
				.filter(x -> x.getUseClassification().value == UseClassification.UseClass_Use.value)
				.collect(Collectors.toList());
		// Check exist and active
		if (optClosures.isEmpty()) {
			return Collections.emptyMap();
		}

		optClosures.forEach(closure -> {
			// Get Processing Ym 処理年月
			YearMonth processingYm = closure.getClosureMonth().getProcessingYm();
			DatePeriod closurePeriod = ClosureService.getClosurePeriod(closure, processingYm);
			resultExport.put(closure.getClosureId().value, closurePeriod);
		});

		return resultExport;

	}
	
	@RequiredArgsConstructor
	class RequireImpl implements ShClosurePubImpl.Require{
		private final CacheCarrier cacheCarrier;
		@Override
		public Optional<Closure> findById(int closureId) {
//			ClosureCache cache = cacheCarrier.get(ClosureCache.DOMAIN_NAME);
//			return cache.get(closureId);
			return closureRepo.findById(AppContexts.user().companyId(), closureId);
			
		}
		
	}
	public static interface Require{
//		closureRepo.findById(cId, closureId);
		Optional<Closure> findById(int closureId);
	}
	
}
