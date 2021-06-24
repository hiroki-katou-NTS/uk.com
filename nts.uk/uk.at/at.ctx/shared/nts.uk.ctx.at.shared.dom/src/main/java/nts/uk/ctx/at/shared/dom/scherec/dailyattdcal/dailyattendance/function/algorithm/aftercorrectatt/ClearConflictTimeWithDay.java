package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone.ConfirmSetSpecifiResult;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * @author ThanhNX
 * 
 *         矛盾した時刻をクリアする
 * 
 */
@Stateless
public class ClearConflictTimeWithDay {

	@Inject
	private ConfirmSetSpecifiTimeZone confirmSetSpecifiTimeZone;

	@Inject
	private CreateResultClearAutoStamp createResultClearAutoStamp;

	// 矛盾した時刻をクリアする
	public void clear(String companyId, WorkingConditionItem workCondItem,
			IntegrationOfDaily domainDaily, Optional<TimeLeavingOfDailyAttd> attendanceLeave) {

		// 所定時間帯をセットするか確認する
		ConfirmSetSpecifiResult confirmSetSpecifi = confirmSetSpecifiTimeZone.confirmset(companyId, workCondItem,
				domainDaily.getWorkInformation(), attendanceLeave, domainDaily.getYmd(), domainDaily.getEditState());

		// 自動打刻をクリアした結果を作成する ---mapping design domain
		if (confirmSetSpecifi.getAutoStampSetClassifi().isPresent()) {
			// 日別実績の出退勤を変更する
			// 返ってきた「日別実績の出退勤、編集状態を返す
			createResultClearAutoStamp.create(confirmSetSpecifi.getAutoStampSetClassifi().get(),
					domainDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v(), attendanceLeave);
		}

	}

}
