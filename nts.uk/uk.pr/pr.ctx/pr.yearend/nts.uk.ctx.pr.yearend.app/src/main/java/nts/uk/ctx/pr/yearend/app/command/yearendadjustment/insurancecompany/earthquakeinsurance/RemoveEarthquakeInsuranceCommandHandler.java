package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.earthquakeinsurance;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsuranceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveEarthquakeInsuranceCommandHandler extends CommandHandler<EarthquakeInsuranceCommand> {

    @Inject
    private EarthquakeInsuranceRepository repository;

    @Override
    protected void handle(CommandHandlerContext<EarthquakeInsuranceCommand> context) {
        String cid = AppContexts.user().companyId();
        String earthquakeCode = context.getCommand().getEarthquakeInsuranceCode();
        repository.remove(cid, earthquakeCode);
    }
}
