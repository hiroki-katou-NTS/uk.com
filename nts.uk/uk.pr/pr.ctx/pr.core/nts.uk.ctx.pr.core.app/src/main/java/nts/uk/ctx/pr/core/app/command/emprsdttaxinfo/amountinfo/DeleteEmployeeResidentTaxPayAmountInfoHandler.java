package nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class DeleteEmployeeResidentTaxPayAmountInfoHandler
        extends CommandHandler<DeleteEmployeeResidentTaxPayAmountInfoCommand> {
    @Inject
    private EmployeeResidentTaxPayAmountInfoRepository empRsdtTaxPayAmountInfoRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteEmployeeResidentTaxPayAmountInfoCommand> commandHandlerContext) {
        DeleteEmployeeResidentTaxPayAmountInfoCommand cmd = commandHandlerContext.getCommand();
        // ドメインモデル「社員住民税納付額情報」を削除する
        empRsdtTaxPayAmountInfoRepo.removeAll(cmd.getListSId(), cmd.getYear());
    }
}
