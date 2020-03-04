package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.CopyShiftMasterByOrgService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 職場で使うシフトマスタを登録する
 */
@Stateless
public class CopyShiftMasterOrgCommandHandler extends CommandHandler<CopyShiftMasterOrgCommand> {

	@Inject
	private ShiftMasterOrgRepository shiftMasterOrgRepo;
	
	@Inject
	private CopyShiftMasterByOrgService copyShiftMasterByOrgService;

	@SuppressWarnings("static-access")
	@Override
	protected void handle(CommandHandlerContext<CopyShiftMasterOrgCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		CopyShiftMasterOrgCommand cmd = context.getCommand();
		List<RegisterShiftMasterOrgCommand> targets = cmd.getToWkps();
		CopyShiftMasterByOrgService.Require required = new RequireImpl(shiftMasterOrgRepo);
		
		Optional<AtomTask> oPersist;
		Optional<ShiftMasterOrganization> copyFrom = shiftMasterOrgRepo.getByTargetOrg(companyId, cmd.toTarget());
		if(!CollectionUtil.isEmpty(targets) && copyFrom.isPresent()) {
			ShiftMasterOrganization fromDomain = copyFrom.get();
			for(RegisterShiftMasterOrgCommand target : targets) {
				oPersist = copyShiftMasterByOrgService.copyShiftMasterByOrg(required, companyId, fromDomain, target.toTarget(), true);
				AtomTask persist = oPersist.get();
				transaction.execute(() -> {
					persist.run();
				});
			}
		}
		
	}
	
	@AllArgsConstructor
	private class RequireImpl implements CopyShiftMasterByOrgService.Require {
		
		@Inject
		private ShiftMasterOrgRepository shiftMasterOrgRepo;
		
		@Override
		public boolean exists(String companyId, TargetOrgIdenInfor targetOrg) {
			return shiftMasterOrgRepo.exists(companyId, targetOrg);
		}

		@Override
		public void insert(ShiftMasterOrganization shiftMaterOrganization) {
			shiftMasterOrgRepo.insert(shiftMaterOrganization);
		}

		@Override
		public void delete(String companyId, TargetOrgIdenInfor targetOrg) {
			shiftMasterOrgRepo.delete(companyId, targetOrg);
		}
		
	}
	
	
}
