/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakTimeDayDto;
import nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto.DiffTimeWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.FixedWorkSettingFinder;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.FixedWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.FlexWorkSettingFinder;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.dto.FlexWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.flowset.dto.FlWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkTimeSettingInfoFinder.
 */
@Stateless
public class WorkTimeSettingInfoFinder {

	/** The work time setting repository. */
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	/** The predetemine time setting repository. */
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	/** The fixed work setting repository. */
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	/** The diff time work setting repository. */
	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	/** The flow work setting repository. */
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;

	/** The flex work setting repository. */
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;
	
	@Inject
	private FixedWorkSettingFinder fixedFinder;
	
//	@Inject
//	private DiffTimeWorkSettingFinder diffFinder;
	
	@Inject
	private FlexWorkSettingFinder flexFinder;
	

	/**
	 * Find.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return the work time setting info dto
	 */
	public WorkTimeSettingInfoDto find(String workTimeCode) {

		String companyId = AppContexts.user().companyId();

		WorkTimeSettingDto workTimeSettingDto = new WorkTimeSettingDto();
		PredetemineTimeSettingDto predetemineTimeSettingDto = new PredetemineTimeSettingDto();
		FixedWorkSettingDto fixedWorkSettingDto = new FixedWorkSettingDto();
		DiffTimeWorkSettingDto diffTimeWorkSettingDto = new DiffTimeWorkSettingDto();
		FlWorkSettingDto flowWorkSettingDto = new FlWorkSettingDto();
		FlexWorkSettingDto flexWorkSettingDto = new FlexWorkSettingDto();

		Optional<WorkTimeSetting> workTimeSettingOp = workTimeSettingRepository.findByCode(companyId, workTimeCode);
		if (workTimeSettingOp.isPresent()) {
			WorkTimeSetting workTimeSetting = workTimeSettingOp.get();

			// find predetemineTimeSettingRepository
			PredetemineTimeSetting predetemineTimeSetting = this.predetemineTimeSettingRepository
					.findByWorkTimeCode(companyId, workTimeCode).get();

			workTimeSetting.saveToMemento(workTimeSettingDto);
			//
			predetemineTimeSetting.saveToMemento(predetemineTimeSettingDto);
			
			// check mode of worktime
			if (workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().equals(WorkTimeDailyAtr.REGULAR_WORK)) {
				// workTimeSettingDto
				// check WorkTimeMethodSet

				switch (workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
				case FIXED_WORK:
					FixedWorkSetting fixedWorkSetting = this.fixedWorkSettingRepository.findByKey(companyId, workTimeCode)
							.get();
					fixedWorkSetting.saveToMemento(fixedWorkSettingDto);
					break;
				case DIFFTIME_WORK:
					DiffTimeWorkSetting diffTimeWorkSetting = this.diffTimeWorkSettingRepository
							.find(companyId, workTimeCode).get();
					diffTimeWorkSetting.saveToMemento(diffTimeWorkSettingDto);
					break;
				case FLOW_WORK:
					FlowWorkSetting flowWorkSetting = this.flowWorkSettingRepository.find(companyId, workTimeCode)
							.get();
					flowWorkSetting.saveToMemento(flowWorkSettingDto);
					break;
				default:
					break;
				}
			} else// case FLEX_WORK
			{
				FlexWorkSetting flexWorkSetting = this.flexWorkSettingRepository.find(companyId, workTimeCode).get();
				flexWorkSetting.saveToMemento(flexWorkSettingDto);
			}
		}

		return new WorkTimeSettingInfoDto(predetemineTimeSettingDto, workTimeSettingDto, flexWorkSettingDto,
				fixedWorkSettingDto, flowWorkSettingDto, diffTimeWorkSettingDto);
	}
	
	public BreakTimeDayDto findModeMethod(String workTimeCode) {

		String companyId = AppContexts.user().companyId();
		
		BreakTimeDayDto breakTimeDto = new BreakTimeDayDto();

		Optional<WorkTimeSetting> workTimeSettingOp = workTimeSettingRepository.findByCode(companyId, workTimeCode);
		if (workTimeSettingOp.isPresent()) {
			WorkTimeSetting workTimeSetting = workTimeSettingOp.get();
			// check mode of worktime
			if (workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().equals(WorkTimeDailyAtr.REGULAR_WORK)) {
				// check WorkTimeMethodSet
				switch (workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
					case FIXED_WORK:
						Optional<FixedWorkSetting> opFixedWorkSetting = this.fixedWorkSettingRepository.findByKey(companyId, workTimeCode);
						
						breakTimeDto = this.fixedFinder.getBreakTimeDtos(opFixedWorkSetting);
//						break;
//					case DIFFTIME_WORK:
//						Optional<DiffTimeWorkSetting> diffTimeWorkSetting = this.diffTimeWorkSettingRepository
//								.find(companyId, workTimeCode).get();
//						breakTimeDto = this.diffFinder.getBreakTimeDtos(diffTimeWorkSetting);
//						break;
//					case FLOW_WORK:
//						FlowWorkSetting flowWorkSetting = this.flowWorkSettingRepository.find(companyId, workTimeCode)
//								.get();
//						breakTimeDto = this.fixeFinder.getBreakTimeDtos(opFixedWorkSetting);
//						break;
					default:
						break;
				}
			} 
			else// case FLEX_WORK
			{
				FlexWorkSetting flexWorkSetting = this.flexWorkSettingRepository.find(companyId, workTimeCode).get();
				breakTimeDto = this.flexFinder.getBreakTimeDtos(flexWorkSetting);
			}
		}
		return breakTimeDto;
	}
}
