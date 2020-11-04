package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone.ConfirmSetSpecifiResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
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
	public void clear(String companyId, String employeeId, GeneralDate date, WorkingConditionItem workCondItem,
			WorkInfoOfDailyAttendance workInformation, Optional<TimeLeavingOfDailyAttd> attendanceLeave,
			List<EditStateOfDailyAttd> editState) {

		// 所定時間帯をセットするか確認する
		ConfirmSetSpecifiResult confirmSetSpecifi = confirmSetSpecifiTimeZone.confirmset(companyId, workCondItem,
				workInformation, attendanceLeave, date);

		// 自動打刻をクリアした結果を作成する ---mapping design domain
		if (confirmSetSpecifi.getAutoStampSetClassifi().isPresent()) {
			// 日別実績の出退勤を変更する
			// 返ってきた「日別実績の出退勤、編集状態を返す
			createResultClearAutoStamp.create(employeeId, date, confirmSetSpecifi.getAutoStampSetClassifi().get(),
					workInformation.getRecordInfo().getWorkTypeCode().v(), attendanceLeave, editState);
		}

	}

}
