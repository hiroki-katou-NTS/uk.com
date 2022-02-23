package nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 応援許可する組織を削除する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援許可する組織.App.応援許可する組織を削除する.応援許可する組織を削除する
 */
@Stateless
public class DeleteSupportAllowOrgCommandHandler extends CommandHandler<DeleteSupportAllowOrgCommand> {
    @Inject
    private SupportAllowOrganizationRepository supportAllowOrganizationRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteSupportAllowOrgCommand> commandHandlerContext) {
        DeleteSupportAllowOrgCommand command = commandHandlerContext.getCommand();

        TargetOrgIdenInfor targetOrgIdenInfor = command.getUnit() == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(command.getWorkplaceId())
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(command.getWorkplaceGroupId());

        supportAllowOrganizationRepo.delete(AppContexts.user().companyId(), targetOrgIdenInfor);
    }
}
