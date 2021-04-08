package nts.uk.ctx.at.function.app.command.outputworkanualsetting;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Command : 年間勤務台帳の設定の詳細を削除する
 */
@Stateless
public class DeleteAnualWorkledgerSettingCommandHandler extends CommandHandler<DeleteAnualWorkledgerSettingCommand> {
    @Inject
    private AnnualWorkLedgerOutputSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<DeleteAnualWorkledgerSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        this.repository.deleteSettingDetail(command.getSettingId());
    }
}
