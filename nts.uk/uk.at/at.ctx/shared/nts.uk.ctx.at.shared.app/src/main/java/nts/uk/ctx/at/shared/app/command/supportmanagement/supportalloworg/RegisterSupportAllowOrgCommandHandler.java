package nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganization;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 応援許可する組織を登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援許可する組織.App.応援許可する組織を登録する.応援許可する組織を登録する
 */
@Stateless
public class RegisterSupportAllowOrgCommandHandler extends CommandHandler<RegisterSupportAllowOrgCommand> {
    @Inject
    private SupportAllowOrganizationRepository supportAllowOrgRepo;

    @Override
    protected void handle(CommandHandlerContext<RegisterSupportAllowOrgCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();

        TargetOrgIdenInfor targetOrgIdenInfor = this.createOrg(command.getOrgUnit(), command.getOrgId());

        // get(会社ID、対象組織情報): List<応援可能組織>
        List<SupportAllowOrganization> supportableOrgList = supportAllowOrgRepo.getListByTargetOrg(cid, targetOrgIdenInfor);
        if (supportableOrgList.isEmpty()) {
            val supportAllowOrgs = command.getOrgCanBeSupports()
                    .stream()
                    .map(org -> SupportAllowOrganization.create(targetOrgIdenInfor, this.createOrg(command.getOrgUnit(), org.getOrgId())))
                    .collect(Collectors.toList());
            if (!supportAllowOrgs.isEmpty()) {
                supportAllowOrgRepo.insertAll(cid, supportAllowOrgs);
            }
        } else {
            supportAllowOrgRepo.delete(AppContexts.user().companyId(), targetOrgIdenInfor);
            val supportAllowOrgs = command.getOrgCanBeSupports()
                    .stream()
                    .map(org -> SupportAllowOrganization.create(targetOrgIdenInfor, this.createOrg(command.getOrgUnit(), org.getOrgId())))
                    .collect(Collectors.toList());
            supportAllowOrgRepo.insertAll(cid, supportAllowOrgs);
        }
    }

    private TargetOrgIdenInfor createOrg(int orgUnit, String orgId) {
        return orgUnit == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(orgId)
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);
    }
}
