package dailyattdcal.dailywork.algorithm.aftercorrectwork;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.Reflection;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.algorithm.CorrectActualReflect;
import nts.uk.ctx.at.record.dom.workinformation.enums.ClassificationReflect;

/**
 * @author ThanhNX
 *
 *         勤務情報変更後の補正
 */
@Stateless
public class CorrectionAfterChangeWorkInfo {

	@Inject
	private CorrectionShortWorkingHour correctShortWorkingHour;

	@Inject
	private CorrectActualReflect correctActualReflect;

	public IntegrationOfDaily correction(String companyId, IntegrationOfDaily domainDaily) {

		// 短時間勤務の補正
		IntegrationOfDaily domainCorrect = correctShortWorkingHour.correct(companyId, domainDaily);

		// TODO: ドメインモデル「予実反映」を取得 - mock new domain
		Reflection reflection = new Reflection(companyId, ClassificationReflect.REFLECT);

		// 予実反映処理の補正
		Pair<WorkInfoOfDailyPerformance, List<EditStateOfDailyPerformance>> correct = correctActualReflect
				.process(companyId, domainDaily.getWorkInformation(), domainDaily.getEditState(), reflection);
		domainCorrect.setWorkInformation(correct.getLeft());
		domainCorrect.setEditState(correct.getRight());
		
		return domainCorrect;
	}

}
