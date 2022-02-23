package nts.uk.ctx.at.shared.app.find.supportmanagement.supportalloworg;

import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganization;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class SupportAllowOrganizationFinder {
    @Inject
    private SupportAllowOrganizationRepository supportAllowOrganizationRepo;

    /**
     * 対象組織を指定して応援許可する組織を取得する
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援許可する組織.App.対象組織を指定して応援許可する組織を取得する.対象組織を指定して応援許可する組織を取得する
     *
     * @param cid       会社ID
     * @param targetOrg 対象組織識別情報
     * @return List<応援許可する組織>
     */
    public List<SupportAllowOrganization> getListByTargetOrg(String cid, TargetOrgIdenInfor targetOrg) {
        return supportAllowOrganizationRepo.getListByTargetOrg(cid, targetOrg);
    }

    /**
     * 応援可能組織を指定して応援許可する組織を取得する
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援許可する組織.App.応援可能組織を指定して応援許可する組織を取得する.応援可能組織を指定して応援許可する組織を取得する
     *
     * @param cid            会社ID
     * @param supportableOrg 対象組織識別情報
     * @return List<応援許可する組織>
     */
    public List<SupportAllowOrganization> getListBySupportableOrg(String cid, TargetOrgIdenInfor supportableOrg) {
        return supportAllowOrganizationRepo.getListBySupportableOrg(cid, supportableOrg);
    }
}
