package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceName;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceNameRepository;

@Stateless
@Transactional
public class UpdateSalaryPerUnitPriceNameCommandHandler extends CommandHandler<SalaryPerUnitPriceNameCommand>
{
    
    @Inject
    private SalaryPerUnitPriceNameRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryPerUnitPriceNameCommand> context) {
        SalaryPerUnitPriceNameCommand command = context.getCommand();
        repository.update(new SalaryPerUnitPriceName(command.getCid(), command.getCode(), command.getName(), command.getAbolition(), command.getShortName(), command.getIntegrationCode(), command.getNote()));
    
    }
}
