package nts.uk.screen.at.app.kmt013;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganizationRepository;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 応援機能の利用と設定した組織リストを取得する
 */
@Stateless
public class SupportFuncGetOrganizationScreenQuery {
    @Inject
    private SupportOperationSettingRepository  supportOperationSettingRepository;

    @Inject
    private SupportAllowOrganizationRepository supportAllowOrganizationRepository;

    /**
     * 取得する
     * @return List<対象組織情報>
     */
    public List<SupportFuncGetOrganizationDto> get(){
        // 1. 取得する(ログイン会社ID) : 応援の運用設定
        val supportOperationSetting = supportOperationSettingRepository.get(AppContexts.user().companyId());

        // 2. 応援の運用設定.利用するか==FALSE
        if (!supportOperationSetting.isSupportDestinationCanSpecifySupporter()){
            throw new BusinessException("Msg_3240");
        }

        // 3. 対象組織情報を取得する(会社ID) : List<対象組織情報> TODO
        val targetOrg = supportAllowOrganizationRepository.getByCid(AppContexts.user().companyId());

        return targetOrg.stream().map(i-> new SupportFuncGetOrganizationDto(i.getTargetOrg().getUnit().value,i.getTargetOrg().getUnit().value == TargetOrganizationUnit.WORKPLACE.value ? i.getTargetOrg().getWorkplaceId().orElse(null) :i.getTargetOrg().getWorkplaceGroupId().orElse(null))).distinct().collect(Collectors.toList());
    }
}
