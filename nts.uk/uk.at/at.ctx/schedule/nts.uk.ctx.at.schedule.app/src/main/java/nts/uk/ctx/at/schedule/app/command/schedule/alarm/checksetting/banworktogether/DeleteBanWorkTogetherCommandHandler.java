package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.C: 同時出勤禁止.メニュー別OCD.同時出勤禁止を削除する
 *
 * @author hai.tt
 */
@Stateless
public class DeleteBanWorkTogetherCommandHandler extends CommandHandler<DeleteBanWorkTogetherCommand> {

    @Inject
    private BanWorkTogetherRepository banWorkTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteBanWorkTogetherCommand> context) {
        DeleteBanWorkTogetherCommand command = context.getCommand();
        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(command.getTargetOrgIdenInfor().getUnit(), TargetOrganizationUnit.class),
                command.getTargetOrgIdenInfor().getWorkplaceId() == null ? Optional.empty() : Optional.of(command.getTargetOrgIdenInfor().getWorkplaceId()),
                command.getTargetOrgIdenInfor().getWorkplaceGroupId() == null ? Optional.empty() : Optional.of(command.getTargetOrgIdenInfor().getWorkplaceGroupId())
        );
        banWorkTogetherRepo.delete(AppContexts.user().companyId(),targetOrgIdenInfor, new BanWorkTogetherCode(command.getCode()));
    }
}
