package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.functionalgorithm.setautomatic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.functionalgorithm.setautomatic.AddTimeZoneToAttendance.AttLeavAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

/**
 * @author thanh_nx
 *
 *         出勤系時刻を丸める
 */
@Stateless
public class RoundAttendanceTime {
	
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepo;

	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepo;

	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepo;

	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;

	public int process(String companyId, int time, String workTimeCode, AttLeavAtr atr) {

		// クラス「就業時間帯の共通設定」を確認する (Xác nhận class 「就業時間帯の共通設定」)
		RequireImpl impl = new RequireImpl(flowWorkSettingRepo, diffTimeWorkSettingRepo, flexWorkSettingRepo,
				fixedWorkSettingRepo, workTimeSettingRepo);
		Optional<WorkTimezoneCommonSet> commonSet = GetCommonSet.workTimezoneCommonSet(impl, companyId, workTimeCode);

		if (!commonSet.isPresent())
			return time;
		// 【パラメータ】時刻を丸める (param thời gian làm tròn)
		// 出勤
		RoundingSet atendanceRoundingSet = commonSet.get().getStampSet().getRoundingSets().stream().filter(item -> item
				.getSection() == (atr == AttLeavAtr.GOING_TO_WORK ? Superiority.ATTENDANCE : Superiority.OFFICE_WORK))
				.findFirst().orElse(null);

		int attendanceTimeAfterRouding = atendanceRoundingSet != null
				? this.roudingTime(time, atendanceRoundingSet.getRoundingSet().getFontRearSection().value,
						new Integer(atendanceRoundingSet.getRoundingSet().getRoundingTimeUnit().description).intValue())
				: time;

		// 【返却】丸め後の時刻 ← 丸めた時刻 (thời gian đã làm tròn)
		return attendanceTimeAfterRouding;

	}

	private int roudingTime(int time, int fontRearSection, int roundingTimeUnit) {

		BigDecimal result = new BigDecimal(time).divide(new BigDecimal(roundingTimeUnit));

		if (!(result.signum() == 0 || result.scale() <= 0 || result.stripTrailingZeros().scale() <= 0)) {
			if (fontRearSection == 0) {
				result = result.setScale(0, RoundingMode.DOWN);
			} else if (fontRearSection == 1) {
				result = result.setScale(0, RoundingMode.UP);
				;
			}
		}
		return result.multiply(new BigDecimal(roundingTimeUnit)).intValue();
	}

	@AllArgsConstructor
	public class RequireImpl implements GetCommonSet.RequireM3 {

		private final FlowWorkSettingRepository flowWorkSettingRepo;

		private final DiffTimeWorkSettingRepository diffTimeWorkSettingRepo;

		private final FlexWorkSettingRepository flexWorkSettingRepo;

		private final FixedWorkSettingRepository fixedWorkSettingRepo;

		private final WorkTimeSettingRepository workTimeSettingRepo;

		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode) {
			return flowWorkSettingRepo.find(companyId, workTimeCode);
		}

		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, String workTimeCode) {
			return flexWorkSettingRepo.find(companyId, workTimeCode);
		}

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, String workTimeCode) {
			return fixedWorkSettingRepo.findByKey(companyId, workTimeCode);
		}

		@Override
		public Optional<DiffTimeWorkSetting> diffTimeWorkSetting(String companyId, String workTimeCode) {
			return diffTimeWorkSettingRepo.find(companyId, workTimeCode);
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode) {
			return workTimeSettingRepo.findByCode(companyId, workTimeCode);
		}

	}
}
