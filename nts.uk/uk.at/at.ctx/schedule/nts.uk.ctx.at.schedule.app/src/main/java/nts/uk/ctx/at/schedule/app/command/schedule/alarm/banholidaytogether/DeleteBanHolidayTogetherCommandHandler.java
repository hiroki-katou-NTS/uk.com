package nts.uk.ctx.at.schedule.app.command.schedule.alarm.banholidaytogether;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 同日休日禁止を削除する
 */
@Stateless
public class DeleteBanHolidayTogetherCommandHandler extends CommandHandler<DeleteBanHolidayTogetherDto> {
    @Inject
    private BanHolidayTogetherRepository banHolidayTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteBanHolidayTogetherDto> context) {
        DeleteBanHolidayTogetherDto command = context.getCommand();

        String companyId = AppContexts.user().companyId();

        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(command.getUnit(), TargetOrganizationUnit.class),
                Optional.ofNullable(command.getWorkplaceId()),
                Optional.ofNullable(command.getWorkplaceGroupId())
        );

        BanHolidayTogetherCode banHolidayCode = new BanHolidayTogetherCode(command.getBanHolidayTogetherCode());

        banHolidayTogetherRepo.delete(companyId, targeOrg, banHolidayCode);
    }
}
