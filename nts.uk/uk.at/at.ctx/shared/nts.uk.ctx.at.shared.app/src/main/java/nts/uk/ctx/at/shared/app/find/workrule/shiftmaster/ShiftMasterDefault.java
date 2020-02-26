package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.CopyShiftMasterByOrgService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.MakeShiftMasterService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.UpdateShiftMasterService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class ShiftMasterDefault {

	@Inject
	private ShiftMasterOrgRepository shiftMasterOrgRp;

	@Inject
	private ShiftMasterRepository shiftMasterRepo;

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
		public boolean checkExists(String companyId, String workTypeCd, String workTimeCd) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void insert(ShiftMaster shiftMater, String workTypeCd, String workTimeCd) {
			// TODO Auto-generated method stub

		}
	}

	@AllArgsConstructor
	private static class RequireImpl_2 implements UpdateShiftMasterService.Require {
		
		private final String companyId = AppContexts.user().companyId();
		
		@Override
		public void update(ShiftMaster shiftMater) {
			// TODO Auto-generated method stub

		}

		@Override
		public Optional<ShiftMaster> getByShiftMaterCd(String shiftMaterCode) {
			// TODO Auto-generated method stub
			return Optional.empty();
		}

		@Override
		public Optional<ShiftMaster> getByWorkTypeAndWorkTime(String workTypeCd, String workTimeCd) {
			// TODO Auto-generated method stub
			return Optional.empty();
		}

	}
	
	@AllArgsConstructor
	private static class RequireImpl_3 implements WorkInformation.Require {
		
		private final String companyId = AppContexts.user().companyId();
		
		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			// TODO Auto-generated method stub
			return null;
		}


	}
}
