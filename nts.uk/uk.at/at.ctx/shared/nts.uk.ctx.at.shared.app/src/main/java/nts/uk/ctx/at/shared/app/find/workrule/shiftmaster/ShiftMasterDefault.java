package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.CopyShiftMasterByOrgService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.MakeShiftMasterService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterImportCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.UpdateShiftMasterService;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 *
 * @author tutk
 *
 */
@Stateless
public class ShiftMasterDefault {

//	@Inject
//	private ShiftMasterOrgRepository shiftMasterOrgRp;

//	@Inject
//	private ShiftMasterRepository shiftMasterRepo;

	@AllArgsConstructor
	private static class RequireImpl_0 implements CopyShiftMasterByOrgService.Require {

		@Override
		public boolean exists(String companyId, TargetOrgIdenInfor targetOrg) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void insert(ShiftMasterOrganization shiftMaterOrganization) {
			// TODO Auto-generated method stub

		}

		@Override
		public void delete(String companyId, TargetOrgIdenInfor targetOrg) {
			// TODO Auto-generated method stub

		}

	}

	@AllArgsConstructor
	private static class RequireImpl_1 implements MakeShiftMasterService.Require {

		@Override
		public boolean checkExists(WorkTypeCode workTypeCd, Optional<WorkTimeCode> workTimeCd) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean checkExistsByCode(ShiftMasterCode shiftMasterCd) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void insert(ShiftMaster shiftMater) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean checkDuplicateImportCode(ShiftMasterImportCode importCode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@AllArgsConstructor
	private static class RequireImpl_2 implements UpdateShiftMasterService.Require {

//		private final String companyId = AppContexts.user().companyId();

		@Override
		public void update(ShiftMaster shiftMater) {
			// TODO Auto-generated method stub

		}

		@Override
		public Optional<ShiftMaster> getByShiftMaterCd(ShiftMasterCode shiftMasterCode) {
			// TODO Auto-generated method stub
			return Optional.empty();
		}

		@Override
		public Optional<ShiftMaster> getByWorkTypeAndWorkTime(WorkTypeCode workTypeCd, Optional<WorkTimeCode> workTimeCd) {
			// TODO Auto-generated method stub
			return Optional.empty();
		}

		@Override
		public boolean checkDuplicateImportCode(ShiftMasterImportCode importCode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
