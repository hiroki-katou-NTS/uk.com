/**
 *
 */
package nts.uk.screen.at.app.ksu001.validwhenpaste;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * ScreenQuery シフトマスタのチェック
 *
 */
@Stateless
public class ValidDataWhenPaste {

	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Inject
	private WorkTimeSettingService workTimeSettingService;

	public boolean valid(List<ValidDataWhenPasteParam> param) {
		boolean result = true;
		WorkInformation.Require require = new RequireWorkInforImpl(workTypeRepo, workTimeSettingRepository, workTimeSettingService, basicScheduleService);
		for (int i = 0; i < param.size(); i++) {
			WorkInformation workInformation = new WorkInformation(param.get(i).workTypeCode, param.get(i).workTimeCode);
			result = workInformation.checkNormalCondition(require);
			if (result == false) {
				throw new BusinessException("Msg_1728");
			}
		}
		return result;
	}

	@AllArgsConstructor
	private static class RequireWorkInforImpl implements WorkInformation.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepo;
		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;
		@Inject
		private WorkTimeSettingService workTimeSettingService;
		@Inject
		private BasicScheduleService basicScheduleService;
		@Override

		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			 return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}
		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}
		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}
		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}
		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}
	}
}
