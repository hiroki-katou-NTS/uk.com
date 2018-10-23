package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValue;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValueRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateSalGenDateHistoryCommandHandler extends CommandHandler<SalGenDateHistoryCommand> {
    
    @Inject
    private SalGenParaDateHistRepository salGenParaDateHistRepository;

    @Override
    protected void handle(CommandHandlerContext<SalGenDateHistoryCommand> context) {
        SalGenDateHistoryCommand command = context.getCommand();


    }
}
