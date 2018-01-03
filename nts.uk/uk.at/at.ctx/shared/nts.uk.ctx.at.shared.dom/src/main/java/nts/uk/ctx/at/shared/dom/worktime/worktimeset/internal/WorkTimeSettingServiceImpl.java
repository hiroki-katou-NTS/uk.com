/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
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
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;

/**
 * The Class WorkTimeSettingServiceImpl.
 */
@Stateless
public class WorkTimeSettingServiceImpl implements WorkTimeSettingService {

	/** The work time setting repo. */
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;

	/** The fixed work setting repo. */
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepo;

	/** The flex work setting repo. */
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepo;

	/** The flow work setting repo. */
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepo;

	/** The diff time work setting repo. */
	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService#
	 * getStampReflectTimezone(java.lang.String, java.lang.String)
	 */
	@Override
	public List<StampReflectTimezone> getStampReflectTimezone(String companyId,
			String workTimeCode) {

		Optional<WorkTimeSetting> optWorkTimeSetting = workTimeSettingRepo.findByCode(companyId,
				workTimeCode);
		if (!optWorkTimeSetting.isPresent()) {
			return Collections.emptyList();
		}

		switch (optWorkTimeSetting.get().getWorkTimeDivision().getWorkTimeDailyAtr()) {
		case REGULAR_WORK:
			switch (optWorkTimeSetting.get().getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				Optional<FixedWorkSetting> optFixedWorkSetting = this.fixedWorkSettingRepo
						.findByKey(companyId, workTimeCode);
				if (optFixedWorkSetting.isPresent()) {
					return optFixedWorkSetting.get().getLstStampReflectTimezone();
				}
				break;

			case DIFFTIME_WORK:
				Optional<DiffTimeWorkSetting> optDiffTimeWorkSetting = this.diffTimeWorkSettingRepo
						.find(companyId, workTimeCode);
				if (optDiffTimeWorkSetting.isPresent()) {
					return Arrays.asList(optDiffTimeWorkSetting.get().getStampReflectTimezone()
							.getStampReflectTimezone());
				}
				break;
			case FLOW_WORK:
				Optional<FlowWorkSetting> optFlowWorkSetting = this.flowWorkSettingRepo
						.find(companyId, workTimeCode);
				if (optFlowWorkSetting.isPresent()) {
					return optFlowWorkSetting.get().getStampReflectTimezone()
							.getStampReflectTimezones();
				}
				break;

			default:
				return Collections.emptyList();
			}

		case FLEX_WORK:
			Optional<FlexWorkSetting> optFixedWorkSetting = this.flexWorkSettingRepo.find(companyId,
					workTimeCode);
			if (optFixedWorkSetting.isPresent()) {
				return optFixedWorkSetting.get().getLstStampReflectTimezone();
			}
			break;

		default:
			return Collections.emptyList();
		}

		return null;

	}

}
