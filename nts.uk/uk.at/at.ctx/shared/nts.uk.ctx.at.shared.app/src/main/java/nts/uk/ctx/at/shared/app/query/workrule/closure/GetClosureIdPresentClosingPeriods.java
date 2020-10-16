package nts.uk.ctx.at.shared.app.query.workrule.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業締め日.アルゴリズム.Query.全ての締めの処理年月と締め期間を取得する.全ての締めの処理年月と締め期間を取得する
 * @author tutt
 *
 */
public class GetClosureIdPresentClosingPeriods {

	@Inject
	private WorkClosureQueryProcessor workClosureQueryProcessor;
	
	@Inject 
	private GetYearProcessAndPeriod getYearProcessAndPeriod;
	
	/**
	 * 2. 全ての締めの処理年月と締め期間を取得する
	 * 
	 * @param companyId
	 * @return
	 */
	public List<ClosureIdPresentClosingPeriod> get(String companyId) {

		List<ClosureIdPresentClosingPeriod> closingPeriods = new ArrayList<>();

		// アルゴリズム「会社の締めを取得する」を実行する
		List<ClosureResultModel> closureResultModels = workClosureQueryProcessor
				.findClosureByReferenceDate(GeneralDate.today());
		List<Integer> closureIds = closureResultModels.stream().map(c -> c.getClosureId()).collect(Collectors.toList());

		// 取得した締めIDのリストでループする
		for (Integer closureId : closureIds) {

			// アルゴリズム「処理年月と締め期間を取得する」を実行する
			Optional<GetYearProcessAndPeriodDto> presentClosingPeriod = getYearProcessAndPeriod.find(companyId, closureId);

			if (presentClosingPeriod.isPresent()) {
				ClosureIdPresentClosingPeriod closingPeriod = new ClosureIdPresentClosingPeriod();

				//OUTPUT．List＜締めID, 現在の締め期間＞に追加する
				closingPeriod.setClosureId(closureId);
				closingPeriod.setCurrentClosingPeriod(presentClosingPeriod.get());

				closingPeriods.add(closingPeriod);
			}
		}
		return closingPeriods;
	}
}
