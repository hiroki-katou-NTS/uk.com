package nts.uk.ctx.at.record.app.command.monthly.standardtime.monthSetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;

/**
 * 選択した特別条項設定を削除する（年月）
 */
@Stateless
public class DeleteAgrMonthSettingCommandHandler extends CommandHandler<DeleteAgrMonthSettingCommand> {

    @Inject
    private AgreementMonthSettingRepository repo;

    @Override
    protected void handle(CommandHandlerContext<DeleteAgrMonthSettingCommand> context) {
        DeleteAgrMonthSettingCommand command = context.getCommand();
        repo.delete(command.getEmployeeId(),command.getYearMonth());
    }
}
