package nts.uk.ctx.at.record.app.command.monthly.standardtime.yearSetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 選択した特別条項設定を削除する（年度）
 */
@Stateless
public class DeleteAgrYearSettingCommandHandler extends CommandHandler<DeleteAgrYearSettingCommand> {

    @Inject
    private AgreementYearSettingRepository repo;

    @Override
    protected void handle(CommandHandlerContext<DeleteAgrYearSettingCommand> context) {
        DeleteAgrYearSettingCommand command = context.getCommand();
        repo.delete(command.getEmployeeId(),command.getYear());
    }
}
