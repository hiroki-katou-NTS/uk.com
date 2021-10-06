package nts.uk.ctx.exio.app.command.exo.externaloutput;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSet;
import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterOrUpdateExOutputCtgAuthSettingCommandHandler extends CommandHandler<RegisterOrUpdateExOutputCtgAuthSettingCommand> {
    @Inject
    private ExOutCtgAuthSetRepository exOutCtgAuthRepo;

    @Override
    protected void handle(CommandHandlerContext<RegisterOrUpdateExOutputCtgAuthSettingCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        val command = commandHandlerContext.getCommand();
        if (command == null) return;

//        【条件】※更新の場合
//        ・ロールID　　＝ロールID
//        ・機能NO　　＝　権限::利用できる／できない権限の説明.NO
//        ※対象が存在しない場合追加、存在する場合は更新
//         input：
//        ・会社ID
//        ・機能NO
//        ・ロールID　＝ロールリスト選択ロールID
//        ※利用区分Check　＝　ONの場合
//        ・権限::利用できる／できない権限の設定　＝　利用できる
//        ※利用区分Check　＝　OFFの場合
//        ・権限::利用できる／できない権限の設定　＝　利用できない

        val exOutCtgAuthSettings = command.getFunctionAuthSettings().stream().
                map(item -> new AvailabilityPermissionData(companyId, command.getRoleId(), item.getFunctionNo(), item.isAvailable()))
                .collect(Collectors.toList());

        exOutCtgAuthSettings.forEach(item -> {
            val oldEntity = exOutCtgAuthRepo.find(item.cid, item.roleId, item.functionNo());
            if (oldEntity.isPresent())
                exOutCtgAuthRepo.update(new ExOutCtgAuthSet(item));
            else
                exOutCtgAuthRepo.add(new ExOutCtgAuthSet(item));
        });
    }
}
