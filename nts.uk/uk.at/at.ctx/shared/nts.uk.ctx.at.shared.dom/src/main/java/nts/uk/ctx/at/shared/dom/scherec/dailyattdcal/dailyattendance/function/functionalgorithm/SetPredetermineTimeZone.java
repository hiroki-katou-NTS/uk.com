package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.functionalgorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.functionalgorithm.setautomatic.PredetermineTimeSetDetailAuto;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author thanh_nx
 *
 *         所定時間帯をセットする
 */
@Stateless
public class SetPredetermineTimeZone {

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private PredetermineTimeSetDetailAuto predetermineTimeSetDetailAuto;

	public List<TimeLeavingWork> setTimeZone(String companyId, WorkTypeCode workTypeCode, WorkTimeCode workTimeCode,
			AutoStampSetClassifi autoStampClasssifi) {

		// 所定時間帯を取得する
		PredetermineTimeSetForCalc predeter = workTimeSettingService.getPredeterminedTimezone(companyId,
				workTimeCode.v(), workTypeCode.v(), null);

		// 所定時間設定を自動打刻セット詳細に入れる
		// 「出退勤（List）」を返す
		return predetermineTimeSetDetailAuto.process(companyId, workTimeCode.v(), autoStampClasssifi,
				predeter.getTimezones());
	}

}
