package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;

public class GetYearProcessAndPeriod {

	/** The work time hist repo. */
	@Inject
	private ClosureRepository closureRepo;
	
	/**
	 * theo điều tra thì đây có vẻ là dành cho 処理年月と締め期間を取得する
	 * 	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業締め日.アルゴリズム.Query.処理年月と締め期間を取得する.処理年月と締め期間を取得する
	 * đã move từ pubImplement đến
	 * @param cId
	 * @param closureId
	 * @return
	 */
	public Optional<GetYearProcessAndPeriodDto> find(String cId, int closureId) {
		Optional<Closure> optClosure = closureRepo.findById(cId, closureId);

		// Check exist and active
		if (!optClosure.isPresent() || optClosure.get().getUseClassification()
				.equals(UseClassification.UseClass_NotUse)) {
			return Optional.empty();
		}

		Closure closure = optClosure.get();

		// Get Processing Ym 処理年月
		YearMonth processingYm = closure.getClosureMonth().getProcessingYm();

		DatePeriod closurePeriod = ClosureService.getClosurePeriod(closureId, processingYm, optClosure);

		// Return
		return Optional.of(GetYearProcessAndPeriodDto.builder()
										.processingYm(processingYm)
										.closureStartDate(closurePeriod.start())
										.closureEndDate(closurePeriod.end())
										.build());
	}
	
}
