package nts.uk.screen.at.app.kmt013;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.supportmanagement.supportalloworg.SupportAllowOrganizationFinder;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 応援可能な組織情報リストを取得する
 */
@Stateless
public class ListOrgCanBeSupportedScreenQuery {

    @Inject
    private SupportAllowOrganizationFinder supportAllowOrganizationFinder;

    @Inject
    private WorkplaceGroupAdapter workplaceGroupAdapter;

    @Inject
    private WorkplaceExportServiceAdapter serviceAdapter;

    @Inject
    private AffWorkplaceAdapter wplAdapter;
    /**
     * 取得する
     * 年月日: date, 対象組織の単位: int, 組織ID: string
     * @return: List<対象組織識別情報,組織の表示情報>
     */
    public TargetOrgInfoDto get(GeneralDate date, int unit, String orgId ){
        val supportableOrgInfoDtos = new ArrayList<SupportableOrgInfoDto>();
        // 1.1 単位==職場: 職場を指定して識別情報を作成する(職場ID): 対象組織識別情報
        TargetOrgIdenInfor targetOrg = null;
        if (unit == TargetOrganizationUnit.WORKPLACE.value){
            targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace(orgId);
        }
        // 1.2 単位＝＝職場グループ: 職場グループを指定して識別情報を作成する(職場グループID): 対象組織識別情報
        if (unit == TargetOrganizationUnit.WORKPLACE_GROUP.value){
            targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);
        }
        // 2. 取得する(対象組織識別情報): List<応援許可する組織>
        val supportOrg = supportAllowOrganizationFinder.getListByTargetOrg(AppContexts.user().companyId(),targetOrg);

        // 3. Loop 対象組織識別情報 in List<応援許可する組織.応援可能組織>
        // 組織の表示情報を取得する(Require, 年月日): 組織の表示情報
        RequireImpl require = new RequireImpl(workplaceGroupAdapter, serviceAdapter, wplAdapter);
        val targetOrgInfo = targetOrg.getDisplayInfor(require,date);

        supportOrg.forEach(item ->{
            val supportableOrganization = item.getSupportableOrganization();
            val orgInfo = supportableOrganization.getDisplayInfor(require,date);
            val unitOrg = supportableOrganization.getUnit().value;
            supportableOrgInfoDtos.add(new SupportableOrgInfoDto(unitOrg,
                    unitOrg == TargetOrganizationUnit.WORKPLACE.value ?  supportableOrganization.getWorkplaceId().orElse("") : supportableOrganization.getWorkplaceGroupId().orElse(""),
                    orgInfo.getDesignation(),orgInfo.getCode(),orgInfo.getName(),orgInfo.getDisplayName(),orgInfo.getGenericTerm()));
        });

        return  new TargetOrgInfoDto(unit,orgId,
                targetOrgInfo.getDesignation(),targetOrgInfo.getCode(),targetOrgInfo.getName(),targetOrgInfo.getDisplayName(),targetOrgInfo.getGenericTerm(),supportableOrgInfoDtos);
    }

    @AllArgsConstructor
    private class RequireImpl implements TargetOrgIdenInfor.Require {

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
            List<WorkplaceInfo> workplaceInfos = serviceAdapter
                    .getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate).stream()
                    .map(mapper -> new WorkplaceInfo(mapper.getWorkplaceId(),
                            Optional.ofNullable(mapper.getWorkplaceCode()),
                            Optional.ofNullable(mapper.getWorkplaceName()),
                            Optional.ofNullable(mapper.getWorkplaceExternalCode()),
                            Optional.ofNullable(mapper.getWorkplaceGenericName()),
                            Optional.ofNullable(mapper.getWorkplaceDisplayName()),
                            Optional.ofNullable(mapper.getHierarchyCode())))
                    .collect(Collectors.toList());
            return workplaceInfos;
        }

        @Override
        public List<String> getWKPID(String WKPGRPID) {
            String CID = AppContexts.user().companyId();
            return wplAdapter.getWKPID(CID, WKPGRPID);
        }
    }
}

