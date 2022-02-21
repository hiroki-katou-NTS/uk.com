package nts.uk.ctx.at.shared.app.find.supportmanagement.supportalloworg;

import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganization;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.context.AppContexts;

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
     */
    public List<SupportAllowOrganization> getListByTargetOrg(TargetOrgIdenInfor targetOrg){
        return supportAllowOrganizationRepo.getListByTargetOrg(AppContexts.user().companyId(), targetOrg);
    }

    /**
     * 応援可能組織を指定して応援許可する組織を取得する
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援許可する組織.App.応援可能組織を指定して応援許可する組織を取得する.応援可能組織を指定して応援許可する組織を取得する
     * @param supportableOrg
     * @return
     */
    public List<SupportAllowOrganization> getListBySupportableOrg(TargetOrgIdenInfor supportableOrg ) {
        return supportAllowOrganizationRepo.getListBySupportableOrg(AppContexts.user().companyId(), supportableOrg);
    }
}
