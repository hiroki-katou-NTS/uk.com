package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.CopyShiftMasterByOrgService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopyShiftWplGrpCommandHandler
		extends CommandHandlerWithResult<CopyShiftMasterOrgCommand, CopyShiftWplGrpResultDto> {

	@Inject
	private ShiftMasterOrgRepository shiftMasterOrgRepo;

	@Inject
	private WorkplaceGroupAdapter groupAdapter;
	
	@Inject
	private WorkplaceExportServiceAdapter serviceAdapter;
	
	@Inject
	private AffWorkplaceAdapter wplAdapter; 

	@Override
	protected CopyShiftWplGrpResultDto handle(CommandHandlerContext<CopyShiftMasterOrgCommand> context) {

		String companyId = AppContexts.user().companyId();
		CopyShiftMasterOrgCommand cmd = context.getCommand();
		List<RegisterShiftMasterOrgCommand> targets = cmd.getToWkps();
		CopyShiftMasterByOrgService.Require required = new RequireImpl(shiftMasterOrgRepo);
		TargetOrgIdenInfor.Require require_new = new RequireTargetImpl(groupAdapter, serviceAdapter, wplAdapter);
		TargetOrgIdenInfor idenInfor = cmd.toTarget();
		
		
		// 1
		Optional<ShiftMasterOrganization> shiftMasterOrg = shiftMasterOrgRepo.getByTargetOrg(companyId, cmd.toTarget());
		List<ShiftGrpResultDto> results = new ArrayList<>();
		List<DisplayInfoOrganization> infoOrganizations = new ArrayList<>();
		Optional<AtomTask> oPersist;
		// 2
		if (!shiftMasterOrg.isPresent()) {
			for (RegisterShiftMasterOrgCommand target : targets) {
				// 2.1
				results.add(new ShiftGrpResultDto(target.getWorkplaceId(), false));
				DisplayInfoOrganization infoOrganization = idenInfor.getDisplayInfor(require_new, GeneralDate.today());
				infoOrganizations.add(infoOrganization);
			}
		}
		if(!CollectionUtil.isEmpty(targets) && shiftMasterOrg.isPresent()) {
			ShiftMasterOrganization fromDomain = shiftMasterOrg.get();
			for(RegisterShiftMasterOrgCommand target : targets) {
				try {
					// 2.2
					oPersist = CopyShiftMasterByOrgService.copyShiftMasterByOrg(required, companyId, fromDomain, target.toTarget(), true);
					if (oPersist.isPresent()) {
						AtomTask persist = oPersist.get();
						// 3
						transaction.execute(() -> {
							// 2.3
							persist.run();
						});
						// 戻り値（AtomTask）がある：処理済み
						results.add(new ShiftGrpResultDto(target.getWorkplaceId(), true));
					} else {
						// 戻り値（AtomTask）がない：未処理
						results.add(new ShiftGrpResultDto(target.getWorkplaceId(), false));
					}
					DisplayInfoOrganization infoOrganization = idenInfor.getDisplayInfor(require_new, GeneralDate.today());
					infoOrganizations.add(infoOrganization);
					
				} catch (Exception e) {
					results.add(new ShiftGrpResultDto(target.getWorkplaceId(), false));
				}
			}
		}
		List<DisInfoOrgDto> disInfoOrgDtos = infoOrganizations.stream().map(mapper-> new DisInfoOrgDto(mapper.getDesignation(), mapper.getCode(), mapper.getName(), mapper.getDisplayName(), mapper.getGenericTerm())).collect(Collectors.toList());
		CopyShiftWplGrpResultDto dto = new CopyShiftWplGrpResultDto(results, disInfoOrgDtos);
		return dto;
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
	
	@AllArgsConstructor
	private class RequireTargetImpl implements TargetOrgIdenInfor.Require {
		@Inject
		private WorkplaceGroupAdapter groupAdapter;
		
		@Inject
		private WorkplaceExportServiceAdapter serviceAdapter;
		
		@Inject
		private AffWorkplaceAdapter wplAdapter; 
		
		
		@Override
		public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
			
			return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
		}

		@Override
		public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
			String companyId = AppContexts.user().companyId();
			List<WorkplaceInfo> workplaceInfos = serviceAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate).stream()
			.map(mapper-> new WorkplaceInfo(mapper.getWorkplaceId(), Optional.ofNullable(mapper.getWorkplaceCode()), Optional.ofNullable(mapper.getWorkplaceName()), Optional.ofNullable(mapper.getWorkplaceExternalCode()), 
					Optional.ofNullable(mapper.getWorkplaceGenericName()), Optional.ofNullable(mapper.getWorkplaceDisplayName()), Optional.ofNullable(mapper.getHierarchyCode()))).collect(Collectors.toList());
			return workplaceInfos;
		}

		@Override
		public List<String> getWKPID(String WKPGRPID) {
			String CID = AppContexts.user().companyId();
			
			return wplAdapter.getWKPID(CID, WKPGRPID);
		}
	}
}