package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
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
public class CopyShiftMasterOrgCommandHandler extends CommandHandlerWithResult<CopyShiftMasterOrgCommand, List<CopyShiftMasterResultDto>> {

	@Inject
	private ShiftMasterOrgRepository shiftMasterOrgRepo;
	
	@SuppressWarnings("static-access")
	@Override
	protected List<CopyShiftMasterResultDto> handle(CommandHandlerContext<CopyShiftMasterOrgCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		CopyShiftMasterOrgCommand cmd = context.getCommand();
		List<RegisterShiftMasterOrgCommand> targets = cmd.getToWkps();
		CopyShiftMasterByOrgService.Require required = new RequireImpl(shiftMasterOrgRepo);
		
		Optional<AtomTask> oPersist;
		Optional<ShiftMasterOrganization> copyFrom = shiftMasterOrgRepo.getByTargetOrg(companyId, cmd.toTarget());
		
		List<CopyShiftMasterResultDto> results = new ArrayList<>();
		
		if(!copyFrom.isPresent()) {
			for(RegisterShiftMasterOrgCommand target : targets) { 
				results.add(new CopyShiftMasterResultDto(target.getWorkplaceId(), false));
			}
			return results;
		}
		
		if(!CollectionUtil.isEmpty(targets) && copyFrom.isPresent()) {
			ShiftMasterOrganization fromDomain = copyFrom.get();
			for(RegisterShiftMasterOrgCommand target : targets) {
				try {
					oPersist = CopyShiftMasterByOrgService.copyShiftMasterByOrg(required, companyId, fromDomain, target.toTarget(), true);
					if (oPersist.isPresent()) {
						AtomTask persist = oPersist.get();
						transaction.execute(() -> {
							persist.run();
						});
						results.add(new CopyShiftMasterResultDto(target.getWorkplaceId(), true));
					} else {
						results.add(new CopyShiftMasterResultDto(target.getWorkplaceId(), false));
					}
					
				} catch (Exception e) {
					results.add(new CopyShiftMasterResultDto(target.getWorkplaceId(), false));
				}
				
			}
		}
		return results;
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
