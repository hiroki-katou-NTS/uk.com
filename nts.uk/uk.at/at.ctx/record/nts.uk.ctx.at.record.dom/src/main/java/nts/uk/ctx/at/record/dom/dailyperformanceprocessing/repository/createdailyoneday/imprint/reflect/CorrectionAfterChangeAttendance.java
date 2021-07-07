package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;
/**
 * 出退勤変更後の補正
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.日別勤怠を補正する.出退勤変更後の補正.出退勤変更後の補正
 * @author phongtq
 *
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@Stateless
public class CorrectionAfterChangeAttendance {
	@Inject
	private CorrectSupportDataWork supportDataWork;
	
	public SupportDataWork correctionAfterChangeAttendance(IntegrationOfDaily integrationOfDaily) {
		// 出退勤で応援データ補正する
		SupportDataWork dataWork = supportDataWork.correctSupportDataWork(integrationOfDaily);
		// 日別実績workを返す
		return dataWork;
	}
}
