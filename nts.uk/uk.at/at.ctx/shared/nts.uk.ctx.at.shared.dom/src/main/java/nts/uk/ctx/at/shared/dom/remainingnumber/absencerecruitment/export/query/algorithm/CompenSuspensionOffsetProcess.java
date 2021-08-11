package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.CalcDigestionCateExtinctionDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.OffsetChronologicalOrder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.TypeOffsetJudgment;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.vacation.algorithm.GetSettingSuspensionPeriod;
import nts.uk.ctx.at.shared.dom.vacation.algorithm.TimeLapseVacationSetting;

/**
 * @author ThanhNX
 *
 *         振出振休の相殺処理
 */
public class CompenSuspensionOffsetProcess {

	private CompenSuspensionOffsetProcess() {
	};

	public static Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> process(Require require, String companyId, String employeeId,
			GeneralDate date, List<AccumulationAbsenceDetail> lstAbsRec) {

		// 期間内の振休の設定を取得する
		List<TimeLapseVacationSetting> lstTimeLapSet = GetSettingSuspensionPeriod.getSetting(require, companyId,
				employeeId);

		// アルゴリズム「時系列順で相殺する」を実行する
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> lstSeqVacation = OffsetChronologicalOrder.process(require, employeeId,
				lstTimeLapSet, lstAbsRec, TypeOffsetJudgment.ABSENCE);

		// 消化区分と消滅日を計算する
		CalcDigestionCateExtinctionDate.calc(lstAbsRec, date, TypeOffsetJudgment.ABSENCE);
		return lstSeqVacation;
	}

	public static interface Require extends GetSettingSuspensionPeriod.Require, OffsetChronologicalOrder.Require {

	}
}
