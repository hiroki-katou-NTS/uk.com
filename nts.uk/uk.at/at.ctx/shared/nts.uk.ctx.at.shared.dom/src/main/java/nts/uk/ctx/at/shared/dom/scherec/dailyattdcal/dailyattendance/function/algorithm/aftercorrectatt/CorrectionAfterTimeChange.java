package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.workinfo.algorithm.CorrectWorkTimeByWorkType;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * @author ThanhNX
 *
 *         勤怠変更後の補正（日別実績の補正処理）
 */
@Stateless
public class CorrectionAfterTimeChange {

	@Inject
	private CorrectWorkTimeByWorkType correctWorkTimeByWorkType;

	@Inject
	private ClearConflictTime clearConflictTime;

//	@Inject
//	private CorrectRestTime correctRestTime;

	public Pair<ChangeDailyAttendance, IntegrationOfDaily> corection(String companyId, IntegrationOfDaily domainDaily,
			ChangeDailyAttendance changeAtt, Optional<WorkingConditionItem> workCondOpt) {

		if (!workCondOpt.isPresent())
			return Pair.of(changeAtt, domainDaily);

		// 勤務種類に応じて就業時間帯の補正を行う
		correctWorkTimeByWorkType.correct(workCondOpt.get(), domainDaily, changeAtt);

		// TODO: 休憩時間帯をセットし直す dang thiet ke 設計中
		// correctRestTime.correct(domainDaily);

		// 矛盾した時間をクリアする
		clearConflictTime.process(domainDaily);

		return Pair.of(changeAtt, domainDaily);
	}

}
