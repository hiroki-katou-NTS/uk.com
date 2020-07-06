package dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dailyattdcal.dailywork.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone;
import dailyattdcal.dailywork.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone.ConfirmSetSpecifiResult;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
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
	public void clear(String companyId, WorkingConditionItem workCondItem, WorkInfoOfDailyPerformance workInformation,
			Optional<TimeLeavingOfDailyPerformance> attendanceLeave, List<EditStateOfDailyPerformance> editState) {

		// 所定時間帯をセットするか確認する
		ConfirmSetSpecifiResult confirmSetSpecifi = confirmSetSpecifiTimeZone.confirmset(companyId, workCondItem,
				workInformation, attendanceLeave);

		// 自動打刻をクリアした結果を作成する ---mapping design domain
		if (confirmSetSpecifi.getAutoStampSetClassifi().isPresent()) {
			// 日別実績の出退勤を変更する
			// 返ってきた「日別実績の出退勤、編集状態を返す
			createResultClearAutoStamp.create(confirmSetSpecifi.getAutoStampSetClassifi().get(),
					workInformation.getRecordInfo().getWorkTypeCode().v(), attendanceLeave, editState);
		}

	}

}
