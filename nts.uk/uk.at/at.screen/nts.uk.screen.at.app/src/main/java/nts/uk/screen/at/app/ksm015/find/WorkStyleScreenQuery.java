package nts.uk.screen.at.app.ksm015.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.*;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkStyleScreenQuery {

	@Inject
	private	WorkTypeRepository workTypeRepository;

	// 出勤・休日系を判定する
	public Integer getWorkStyle(WorkStyleDto dto) {
		String companyId = AppContexts.user().companyId();
		ShiftMasterDisInfor shiftMasterDisInfor = new ShiftMasterDisInfor(new ShiftMasterName(dto.getShiftMasterName()), new ColorCodeChar6(dto.getColor()),new ColorCodeChar6(dto.getColor()), Optional.of(new Remarks(dto.getRemarks())));
		//TODO 取り込みコード追加
		ShiftMaster shiftMaster = new ShiftMaster(companyId, new ShiftMasterCode(dto.getShiftMasterCode()), shiftMasterDisInfor, dto.getWorkTypeCode(), dto.getWorkTimeCode(), Optional.of(new ShiftMasterImportCode(dto.getImportCode())));
		WorkInformation.Require require = new WorkStyleScreenQueryImpl(workTypeRepository);
		Integer workStyle = shiftMaster.getWorkStyle(require, companyId).get().value;
		return workStyle;
	}

	@AllArgsConstructor
	public static class WorkStyleScreenQueryImpl implements WorkInformation.Require{

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepository;

		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepository.findByPK(companyId, workTypeCode.v());
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return null;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return null;
		}

// fix bug 113211
//		@Override
//		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
//			return null;
//		}

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

	}
}
