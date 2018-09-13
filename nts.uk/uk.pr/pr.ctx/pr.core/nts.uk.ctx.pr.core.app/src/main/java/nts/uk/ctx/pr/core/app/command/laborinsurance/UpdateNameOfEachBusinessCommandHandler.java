package nts.uk.ctx.pr.core.app.command.laborinsurance;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.laborinsurance.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class UpdateNameOfEachBusinessCommandHandler extends CommandHandler<UpdateNameOfEachBusinessCommand> {

    @Inject
    WorkersComInsurService workersComInsurService;

    @Inject
    OccAccInsurBusRepository occAccInsurBusRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateNameOfEachBusinessCommand> context) {
        String cId = AppContexts.user().companyId();
        UpdateNameOfEachBusinessCommand command = context.getCommand();
        Optional<OccAccInsurBus> occAccInsurBus = workersComInsurService.getOccAccInsurBus(cId);
        List<NameOfEachBusiness> listNameOfEachBusiness = command.getListEachBusiness().stream().map(item->
        {
           return  new NameOfEachBusiness(item.getOccAccInsurBusNo(),item.getToUse(),Optional.of(new OccAccInsurBusinessName(item.getName())));
        }).collect(Collectors.toList());
        occAccInsurBus.get().setEachBusiness(listNameOfEachBusiness);
        occAccInsurBusRepository.update(occAccInsurBus.get());
    }
}
