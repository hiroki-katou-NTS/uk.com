package nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.common;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure.ClosureHistPeriod;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure.GetSpecifyPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * @author ThanhNX
 *
 */

@Stateless
public class GenDPCorrectStateService {

	@Inject
	private GetSpecifyPeriod getSpecifyPeriod;
	
	public Optional<DPCorrectStateParam> genStateParamClosureId(DPCorrectStateParam param, ClosureId closureId, YearMonth yearMonth) {
		DPCorrectStateParam result = param;
		// パラメータ「対象締め」をセットする
		result.setClosureId(closureId);
		result.setYearMonth(yearMonth);
		// 指定した年月の締め期間を取得する
		List<ClosureHistPeriod> lstClosureHist = getSpecifyPeriod.getSpecifyPeriod(yearMonth);
		Optional<ClosureHistPeriod> closureHistOpt = lstClosureHist.stream()
				.filter(x -> x.getClosureId().value == closureId.value).findFirst();
		if (!closureHistOpt.isPresent()) {
			return Optional.empty();
		}
		result.setDatePeriod(closureHistOpt.get().getPeriod());
		return Optional.of(result);
	}
}
