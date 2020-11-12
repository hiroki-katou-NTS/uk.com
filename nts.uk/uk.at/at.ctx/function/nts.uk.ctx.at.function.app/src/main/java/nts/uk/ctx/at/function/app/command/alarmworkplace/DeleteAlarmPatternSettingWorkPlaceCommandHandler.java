package nts.uk.ctx.at.function.app.command.alarmworkplace;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * パータン設定を削除する
 */
//@Stateless
//public class DeleteAlarmPatternSettingWorkPlaceCommandHandler extends CommandHandler<DeleteAlarmPatternSettingWorkPlaceDto> {
//    @Inject
//    private BanHolidayTogetherRepository banHolidayTogetherRepo;
//
//    @Override
//    protected void handle(CommandHandlerContext<DeleteAlarmPatternSettingWorkPlaceDto> context) {
//        DeleteBanHolidayTogetherDto command = context.getCommand();
//
//        String companyId = AppContexts.user().companyId();
//
//        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(
//                EnumAdaptor.valueOf(command.getUnit(), TargetOrganizationUnit.class),
//                Optional.ofNullable(command.getWorkplaceId()),
//                Optional.ofNullable(command.getWorkplaceGroupId())
//        );
//
//        BanHolidayTogetherCode banHolidayCode = new BanHolidayTogetherCode(command.getBanHolidayTogetherCode());
//
//        banHolidayTogetherRepo.delete(companyId, targeOrg, banHolidayCode);
//    }
//}
