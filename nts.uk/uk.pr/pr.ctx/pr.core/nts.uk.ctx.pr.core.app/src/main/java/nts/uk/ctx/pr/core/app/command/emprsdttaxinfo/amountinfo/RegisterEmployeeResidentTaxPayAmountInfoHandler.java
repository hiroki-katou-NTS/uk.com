package nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.service.EmployeeResidentTaxPayAmountInfoService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class RegisterEmployeeResidentTaxPayAmountInfoHandler
        extends CommandHandler<RegisterEmployeeResidentTaxPayAmountInfoCommand> {

    @Inject
    private EmployeeResidentTaxPayAmountInfoService empRsdtTaxPayAmountInfoService;

    @Override
    protected void handle(CommandHandlerContext<RegisterEmployeeResidentTaxPayAmountInfoCommand> commandHandlerContext) {
        RegisterEmployeeResidentTaxPayAmountInfoCommand cmd = commandHandlerContext.getCommand();
        List<EmployeeResidentTaxPayAmountInfo> listEmpRsdtTaxPayAmountInfo = cmd.getListEmpPayAmount()
                .stream().map(x -> x.toDomain()).collect(Collectors.toList());
        empRsdtTaxPayAmountInfoService.registerTaxPayAmount(listEmpRsdtTaxPayAmountInfo, cmd.getYear());
    }

}
