package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectwork;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author ThanhNX
 *
 *         勤務情報変更後の補正
 */
@Stateless
public class CorrectionAfterChangeWorkInfo {

	@Inject
	private CorrectionShortWorkingHour correctShortWorkingHour;

	public IntegrationOfDaily correction(String companyId, IntegrationOfDaily domainDaily) {

		// 短時間勤務の補正
		IntegrationOfDaily domainCorrect = correctShortWorkingHour.correct(companyId, domainDaily);

		// fix 111738
		// remove TODO: ドメインモデル「予実反映」を取得 - mock new domain
		//  remove 予実反映処理の補正
		
		return domainCorrect;
	}

}
