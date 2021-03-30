package nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControlRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * スケジュール修正の機能制御を登録する
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM011_スケジュール前準備.C：スケジュール基本の設定.メニュー別OCD.登録時
 *
 * @author viet.tx
 */
@Stateless
@Transactional
public class RegisterScheFuncCtrlCommandHandler extends CommandHandler<RegisterScheFuncCtrlCommand> {

    @Inject
    private ScheFunctionControlRepository scheFunctionControlRepo;

    @Override
    protected void handle(CommandHandlerContext<RegisterScheFuncCtrlCommand> commandHandlerContext) {
        RegisterScheFuncCtrlCommand command = commandHandlerContext.getCommand();
        if (command == null) return;

        String companyId = AppContexts.user().companyId();
        Optional<ScheFunctionControl> scheFuncCtrl = scheFunctionControlRepo.get(companyId);

        if (scheFuncCtrl.isPresent()) {
            scheFunctionControlRepo.update(companyId, command.toDomain());
        } else {
            scheFunctionControlRepo.insert(companyId, command.toDomain());
        }
    }
}
