package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectatt.checkspr.JudgmentSprStamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.common.ClearAutomaticStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
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

	@Inject
	private WorkTypeRepository workTypeRepo;

	// 自動打刻をクリアした結果を作成する
	public void create(String employeeId, GeneralDate date, AutoStampSetClassifi autoStampSetClassifi,
			String workTypeCode, Optional<TimeLeavingOfDailyAttd> attendanceLeave,
			List<EditStateOfDailyAttd> editState) {

		String companyId = AppContexts.user().companyId();
		if (!attendanceLeave.isPresent())
			return;

		EditStateSetting editStateSet = EditStateSetting.HAND_CORRECTION_MYSELF;
		if (!employeeId.equals(AppContexts.user().employeeId())) {
			editStateSet = EditStateSetting.HAND_CORRECTION_OTHER;
		}

		RequireImpl impl = new RequireImpl(companyId, workTypeRepo);
		for (TimeLeavingWork timeLeave : attendanceLeave.get().getTimeLeavingWorks()) {

			// 出勤反映を確認
			if (autoStampSetClassifi.getAttendanceReflect() != NotUseAtr.USE
					&& timeLeave.getAttendanceStamp().isPresent()) {
				boolean checkRemove = JudgmentSprStamp.checkRemove(impl, workTypeCode,
						timeLeave.getAttendanceStamp().get());

				if (checkRemove) {
					// 自動打刻をクリアする
					TimeActualStamp timeActualStamp = clearAutomaticStamp.clear(workTypeCode,
							timeLeave.getAttendanceStamp().get());
					// 変更後の日別実績の出退勤を返す
					timeLeave.setTimeLeavingWork(timeLeave.getWorkNo(), Optional.of(timeActualStamp),
							timeLeave.getLeaveStamp());
					createEditState(editState, editStateSet,
							timeLeave.getWorkNo().v() == 1 ? ITEM_EDIT_ATT1 : ITEM_EDIT_ATT2);
				}
			}

			// 退勤反映を確認
			if (autoStampSetClassifi.getLeaveWorkReflect() != NotUseAtr.USE && timeLeave.getLeaveStamp().isPresent()) {
				boolean checkRemove = JudgmentSprStamp.checkRemove(impl, workTypeCode, timeLeave.getLeaveStamp().get());

				if (checkRemove) {
					// 自動打刻をクリアする
					TimeActualStamp timeActualStamp = clearAutomaticStamp.clear(workTypeCode,
							timeLeave.getLeaveStamp().get());
					// 変更後の日別実績の出退勤を返す
					timeLeave.setTimeLeavingWork(timeLeave.getWorkNo(), timeLeave.getAttendanceStamp(),
							Optional.of(timeActualStamp));
					createEditState(editState, editStateSet,
							timeLeave.getWorkNo().v() == 1 ? ITEM_EDIT_LEAV1 : ITEM_EDIT_LEAV2);
				}

			}
		}

	}

	private void createEditState(List<EditStateOfDailyAttd> editState, EditStateSetting editStateSet,
			List<Integer> itemIds) {

		itemIds.forEach(itemId -> editState.add(new EditStateOfDailyAttd(itemId, editStateSet)));
	}

	@AllArgsConstructor
	public class RequireImpl implements JudgmentSprStamp.Require {

		private final String companyId;

		private final WorkTypeRepository workTypeRepo;

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

	}
}
