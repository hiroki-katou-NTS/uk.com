package nts.uk.ctx.pr.core.app.command.laborinsurance;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.laborinsurance.NameOfEachBusiness;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBus;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusinessName;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateNameOfEachBusinessCommandHandler extends CommandHandler<UpdateNameOfEachBusinessCommand> {

    @Inject
    OccAccInsurBusRepository occAccInsurBusRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateNameOfEachBusinessCommand> context) {
        String cId = AppContexts.user().companyId();
        UpdateNameOfEachBusinessCommand command = context.getCommand();
        List<NameOfEachBusiness> listNameOfEachBusiness = command.getListEachBusiness().stream().map(item->
        {
           return  new NameOfEachBusiness(item.getOccAccInsurBusNo(),item.getToUse(),Optional.of(new OccAccInsurBusinessName(item.getName())));
        }).collect(Collectors.toList());
        OccAccInsurBus occAccInsurBus = new OccAccInsurBus(cId, listNameOfEachBusiness);
        occAccInsurBusRepository.update(occAccInsurBus);
    }
}
