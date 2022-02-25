package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTime;
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
 *         ジャスト遅刻早退時刻を補正する
 */
@Stateless
public class CorrectLateArrivalDepartureTime {

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

	public List<TimeLeavingWork> process(String companyId, String workTimeCode, List<TimeLeavingWork> lstTimeLeavingWork) {

		// ドメインモデル「就業時間帯の設定」を取得する

		Optional<WorkTimezoneCommonSet> setting = GetCommonSet.workTimezoneCommonSet(new RequireImpl(), companyId, workTimeCode);
		if (!setting.isPresent())
			return lstTimeLeavingWork;

		RoundingTime roundingTimeinfo = setting.get().getStampSet().getRoundingTime();
		return  roundingTimeinfo.justTimeCorrectionAutoStamp(lstTimeLeavingWork);

	}
	
	public class RequireImpl implements GetCommonSet.RequireM3{

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
