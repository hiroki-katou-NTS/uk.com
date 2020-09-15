package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.vacation.algorithm.PaidHolidayDuringPeriodSetting;
import nts.uk.ctx.at.shared.dom.vacation.algorithm.TimeLapseVacationSetting;

/**
 * @author ThanhNX
 *
 *         相殺処理
 */
public class OffsetProcessing {

	private OffsetProcessing() {
	};

	public static Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> process(Require require, String companyId, String employeeId,
			GeneralDate date, List<AccumulationAbsenceDetail> accAbse) {

		// 代休設定を全て取得
		List<TimeLapseVacationSetting> lstTimeLapse = PaidHolidayDuringPeriodSetting.getSetting(require, companyId,
				employeeId);

		// 時系列での相殺処理
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> lstSeqVacation = OffsetChronologicalOrder.process(require, employeeId,
				lstTimeLapse, accAbse, TypeOffsetJudgment.REAMAIN);

		// 消化区分と消滅日を計算する
		CalcDigestionCateExtinctionDate.calc(accAbse, date, TypeOffsetJudgment.REAMAIN);

		return lstSeqVacation;

	}

	public static interface Require extends PaidHolidayDuringPeriodSetting.Require, OffsetChronologicalOrder.Require {

	}

}
