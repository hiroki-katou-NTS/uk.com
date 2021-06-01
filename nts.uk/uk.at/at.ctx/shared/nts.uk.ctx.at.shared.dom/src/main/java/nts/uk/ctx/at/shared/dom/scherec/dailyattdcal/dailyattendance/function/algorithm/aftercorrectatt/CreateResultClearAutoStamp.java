package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt.checkspr.JudgmentSprStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.common.ClearAutomaticStamp;
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

	@Inject
	private ClearAutomaticStamp clearAutomaticStamp;

	@Inject
	private WorkTypeRepository workTypeRepo;

	// 自動打刻をクリアした結果を作成する
	public void create(AutoStampSetClassifi autoStampSetClassifi,
			String workTypeCode, Optional<TimeLeavingOfDailyAttd> attendanceLeave) {

		String companyId = AppContexts.user().companyId();
		if (!attendanceLeave.isPresent())
			return;

		RequireImpl impl = new RequireImpl(companyId, workTypeRepo);
		for (TimeLeavingWork timeLeave : attendanceLeave.get().getTimeLeavingWorks()) {

			// 出勤反映を確認
			if (autoStampSetClassifi.getAttendanceReflect() != NotUseAtr.USE
					&& timeLeave.getAttendanceStamp().isPresent()) {
				boolean checkRemove = AppContexts.optionLicense().customize().ootsuka() ? JudgmentSprStamp.checkRemove(impl, workTypeCode,
						timeLeave.getAttendanceStamp().get()) : true;

				if (checkRemove) {
					// 自動打刻をクリアする
					TimeActualStamp timeActualStamp = clearAutomaticStamp.clear(workTypeCode,
							timeLeave.getAttendanceStamp().get());
					// 変更後の日別実績の出退勤を返す
					timeLeave.setTimeLeavingWork(timeLeave.getWorkNo(), Optional.of(timeActualStamp),
							timeLeave.getLeaveStamp());
				}
			}

			// 退勤反映を確認
			if (autoStampSetClassifi.getLeaveWorkReflect() != NotUseAtr.USE && timeLeave.getLeaveStamp().isPresent()) {
				boolean checkRemove =  AppContexts.optionLicense().customize().ootsuka() ? 
						JudgmentSprStamp.checkRemove(impl, workTypeCode, timeLeave.getLeaveStamp().get()) : true;

				if (checkRemove) {
					// 自動打刻をクリアする
					TimeActualStamp timeActualStamp = clearAutomaticStamp.clear(workTypeCode,
							timeLeave.getLeaveStamp().get());
					// 変更後の日別実績の出退勤を返す
					timeLeave.setTimeLeavingWork(timeLeave.getWorkNo(), timeLeave.getAttendanceStamp(),
							Optional.of(timeActualStamp));
				}

			}
		}

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
