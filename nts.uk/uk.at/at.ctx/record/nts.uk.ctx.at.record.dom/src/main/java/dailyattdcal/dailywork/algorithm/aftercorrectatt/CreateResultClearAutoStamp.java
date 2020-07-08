package dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dailyattdcal.dailywork.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import dailyattdcal.dailywork.algorithm.common.ClearAutomaticStamp;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 *
 *         自動打刻をクリアした結果を作成する
 */
@Stateless
public class CreateResultClearAutoStamp {

	private final List<Integer> ITEM_EDIT_ATT1 = Arrays.asList(30, 31, 36, 37);

	private final List<Integer> ITEM_EDIT_LEAV1 = Arrays.asList(33, 34, 38, 39);

	private final List<Integer> ITEM_EDIT_ATT2 = Arrays.asList(40, 41, 46, 47);

	private final List<Integer> ITEM_EDIT_LEAV2 = Arrays.asList(43, 44, 48, 49);

	@Inject
	private ClearAutomaticStamp clearAutomaticStamp;

	// 自動打刻をクリアした結果を作成する
	public void create(AutoStampSetClassifi autoStampSetClassifi, String workTypeCode,
			Optional<TimeLeavingOfDailyPerformance> attendanceLeave, List<EditStateOfDailyPerformance> editState) {

		if (!attendanceLeave.isPresent())
			return;

		EditStateSetting editStateSet = EditStateSetting.HAND_CORRECTION_MYSELF;
		if (!attendanceLeave.get().getEmployeeId().equals(AppContexts.user().employeeId())) {
			editStateSet = EditStateSetting.HAND_CORRECTION_OTHER;
		}

		for (TimeLeavingWork timeLeave : attendanceLeave.get().getTimeLeavingWorks()) {

			// 出勤反映を確認
			if (autoStampSetClassifi.getAttendanceReflect() != NotUseAtr.USE
					&& timeLeave.getAttendanceStamp().isPresent()) {
				// 自動打刻をクリアする
				TimeActualStamp timeActualStamp = clearAutomaticStamp.clear(workTypeCode,
						timeLeave.getAttendanceStamp().get());
				// 変更後の日別実績の出退勤を返す
				timeLeave.setTimeLeavingWork(timeLeave.getWorkNo(), Optional.of(timeActualStamp),
						timeLeave.getLeaveStamp());
				createEditState(attendanceLeave.get().getEmployeeId(), attendanceLeave.get().getYmd(), editState,
						editStateSet, timeLeave.getWorkNo().v() == 1 ? ITEM_EDIT_ATT1 : ITEM_EDIT_ATT2);
			}

			// 退勤反映を確認
			if (autoStampSetClassifi.getLeaveWorkReflect() != NotUseAtr.USE && timeLeave.getLeaveStamp().isPresent()) {
				// 自動打刻をクリアする
				TimeActualStamp timeActualStamp = clearAutomaticStamp.clear(workTypeCode,
						timeLeave.getLeaveStamp().get());
				// 変更後の日別実績の出退勤を返す
				timeLeave.setTimeLeavingWork(timeLeave.getWorkNo(), timeLeave.getAttendanceStamp(),
						Optional.of(timeActualStamp));
				createEditState(attendanceLeave.get().getEmployeeId(), attendanceLeave.get().getYmd(), editState,
						editStateSet, timeLeave.getWorkNo().v() == 1 ? ITEM_EDIT_LEAV1 : ITEM_EDIT_LEAV2);
			}

		}

	}

	private void createEditState(String employeeId, GeneralDate ymd, List<EditStateOfDailyPerformance> editState,
			EditStateSetting editStateSet, List<Integer> itemIds) {

		itemIds.forEach(
				itemId -> editState.add(new EditStateOfDailyPerformance(employeeId, itemId, ymd, editStateSet)));
	}
}
