package nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.supportmanagement.supportalloworg.SupportAllowOrganizationFinder;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.CopySupportAllowOrganizationService;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganization;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 職場で使う応援を複写する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援許可する組織.App.職場で使う応援を複写する.職場で複写する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CopySupportAllowOrgCommandHandler extends CommandHandlerWithResult<CopySupportAllowOrgCommand, List<CopySupportAllowOrgResult>> {
    @Inject
    private SupportAllowOrganizationRepository supportAllowOrgRepo;

    @Inject
    private SupportAllowOrganizationFinder supportAllowOrgFinder;

    @Inject
    private WorkplaceGroupAdapter groupAdapter;

    @Inject
    private WorkplaceExportServiceAdapter serviceAdapter;

    @Inject
    private AffWorkplaceAdapter wplAdapter;

    @Override
    protected List<CopySupportAllowOrgResult> handle(CommandHandlerContext<CopySupportAllowOrgCommand> commandHandlerContext) {
        CopySupportAllowOrgCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        List<CopySupportAllowOrgResult> resultList = new ArrayList<>();

        // 1. 職場を指定して識別情報を作成する(職場ID): 対象組織の単位＝＝0, 組織ID＝＝複写元職場ID
        TargetOrgIdenInfor sourceTargetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace(command.getCopySourceWkpId());

        // 2.取得する(対象組織識別情報)
        List<SupportAllowOrganization> targetOrgAllows = supportAllowOrgFinder.getListByTargetOrg(cid, sourceTargetOrg);

        // 3. Loop
        command.getCopyDestinationWkpIds().forEach(destinationWkpId -> {
            // 3.1. 職場を指定して識別情報を作成する(職場ID): 対象組織識別情報
            val destinationTargetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace(destinationWkpId);

            // 3.2. 組織の表示情報を取得する(Require, 年月日): 組織の表示情報
            RequireOrgInfoImpl require = new RequireOrgInfoImpl(groupAdapter, serviceAdapter, wplAdapter);
            DisplayInfoOrganization orgDisplayInfo = destinationTargetOrg.getDisplayInfor(require, GeneralDate.today());

            // 3.3.
            SupportAllowOrganization copyDestinationOrg = SupportAllowOrganization.create(sourceTargetOrg, destinationTargetOrg);

            // 3.4. 複写する(Require, List<応援許可する組織>, 対象組織識別情報, boolean)
            RequireCopyImpl requireCopy = new RequireCopyImpl(cid, supportAllowOrgRepo);
            Optional<AtomTask> atomTaskOpt = CopySupportAllowOrganizationService.copy(requireCopy, targetOrgAllows, destinationTargetOrg, command.isOverWrite());

            atomTaskOpt.ifPresent(atomTask -> transaction.execute(atomTask::run));

            resultList.add(new CopySupportAllowOrgResult(
                    destinationWkpId,
                    atomTaskOpt.isPresent(),
                    new OrgDisplayInfoDto(
                            orgDisplayInfo.getCode(),
                            orgDisplayInfo.getDisplayName()
                    )
            ));
        });

        return resultList;
    }

    @AllArgsConstructor
    private class RequireOrgInfoImpl implements TargetOrgIdenInfor.Require {
        private WorkplaceGroupAdapter groupAdapter;
        private WorkplaceExportServiceAdapter serviceAdapter;
        private AffWorkplaceAdapter wplAdapter;

        @Override
        public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
            return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
        }

        @Override
        public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
            return serviceAdapter.getWorkplaceInforByWkpIds(AppContexts.user().companyId(), listWorkplaceId, baseDate).stream()
                    .map(mapper -> new WorkplaceInfo(mapper.getWorkplaceId(), Optional.ofNullable(mapper.getWorkplaceCode()), Optional.ofNullable(mapper.getWorkplaceName()), Optional.ofNullable(mapper.getWorkplaceExternalCode()),
                            Optional.ofNullable(mapper.getWorkplaceGenericName()), Optional.ofNullable(mapper.getWorkplaceDisplayName()), Optional.ofNullable(mapper.getHierarchyCode()))).collect(Collectors.toList());
        }

        @Override
        public List<String> getWKPID(String WKPGRPID) {
            return wplAdapter.getWKPID(AppContexts.user().companyId(), WKPGRPID);
        }
    }

    @AllArgsConstructor
    private class RequireCopyImpl implements CopySupportAllowOrganizationService.Require {
        private String cid;
        private SupportAllowOrganizationRepository supportAllowOrgRepo;

        @Override
        public boolean existsSupportAllowOrganization(TargetOrgIdenInfor targetOrg) {
            return supportAllowOrgRepo.exists(cid, targetOrg);
        }

        @Override
        public void deleteSupportAllowOrganization(TargetOrgIdenInfor targetOrg) {
            supportAllowOrgRepo.delete(cid, targetOrg);
        }

        @Override
        public void registerSupportAllowOrganization(List<SupportAllowOrganization> supportAllowOrgs) {
            supportAllowOrgRepo.insertAll(cid, supportAllowOrgs);
        }
    }
}
