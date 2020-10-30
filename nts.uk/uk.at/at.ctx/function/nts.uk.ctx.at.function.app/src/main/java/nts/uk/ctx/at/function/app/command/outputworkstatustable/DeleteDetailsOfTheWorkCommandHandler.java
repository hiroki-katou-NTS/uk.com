package nts.uk.ctx.at.function.app.command.outputworkstatustable;


import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettingsRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;

/**
 * Command : 勤務状況表の設定の詳細を削除する
 *
 * @author chinh.hm
 */
public class DeleteDetailsOfTheWorkCommandHandler extends CommandHandler<DeleteDetailsOfTheWorkCommand> {
    @Inject
    private WorkStatusOutputSettingsRepository workStatusOutputSettingsRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteDetailsOfTheWorkCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        this.workStatusOutputSettingsRepository.delete(command.getSettingId());
    }
}
