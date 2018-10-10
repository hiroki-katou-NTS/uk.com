package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceName;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceNameRepository;
import nts.uk.shr.com.context.AppContexts;
import sun.awt.AppContext;

import java.util.Optional;

@Stateless
@Transactional
public class AddSalaryPerUnitPriceNameCommandHandler extends CommandHandler<SalaryPerUnitPriceNameCommand>
{
    
    @Inject
    private SalaryPerUnitPriceNameRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryPerUnitPriceNameCommand> context) {
        SalaryPerUnitPriceNameCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();

        Optional<SalaryPerUnitPriceName> salaryPerUnitPriceName = repository.getSalaryPerUnitPriceNameById(cid, command.getCode());

        if(salaryPerUnitPriceName.isPresent()) {
            throw new BusinessException("Msg_3");
        }

        repository.add(new SalaryPerUnitPriceName(cid, command.getCode(), command.getName(), command.getAbolition(), command.getShortName(), command.getIntegrationCode(), command.getNote()));
    
    }
}
