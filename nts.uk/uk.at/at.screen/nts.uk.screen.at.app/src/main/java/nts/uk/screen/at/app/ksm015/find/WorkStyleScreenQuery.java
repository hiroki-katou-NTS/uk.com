package nts.uk.screen.at.app.ksm015.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.Remarks;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkStyleScreenQuery {


	@Inject
	private BasicScheduleService basicScheduleService;

	// 出勤・休日系を判定する
	public Integer getWorkStyle(WorkStyleDto dto) {
		String companyId = AppContexts.user().companyId();
		ShiftMasterDisInfor shiftMasterDisInfor = new ShiftMasterDisInfor(new ShiftMasterName(dto.getShiftMasterName()), new ColorCodeChar6(dto.getColor()), new Remarks(dto.getRemarks()));
		ShiftMaster shiftMaster = new ShiftMaster(companyId, new ShiftMasterCode(dto.getShiftMasterCode()), shiftMasterDisInfor, dto.getWorkTypeCode(), dto.getWorkTimeCode());
		WorkInformation.Require require = new WorkStyleScreenQueryImpl(basicScheduleService);
		Integer workStyle = shiftMaster.getWorkStyle(require).get().value;
		return workStyle;
	}

	@AllArgsConstructor
	public static class WorkStyleScreenQueryImpl implements WorkInformation.Require{

		@Inject
		private BasicScheduleService basicScheduleService;

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return null;
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return null;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return null;
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
			return null;
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}

	}
}
