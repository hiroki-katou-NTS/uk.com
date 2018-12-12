package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance;


import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsurance;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsurance;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsuranceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class CopyCommandHandler extends CommandHandler<List<LifeInsuranceCommand>> {

    @Inject
    private LifeInsuranceRepository repository;

    @Override
    protected void handle(CommandHandlerContext<List<LifeInsuranceCommand>> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        List<LifeInsuranceCommand> command = commandHandlerContext.getCommand();
        if (command.size() == 0) {
            return;
        }
        List<LifeInsurance> lstLifeInsurance = command.stream().map(item -> new LifeInsurance(cid, item.getLifeInsuranceCode(), item.getLifeInsuranceName())).collect(Collectors.toList());

        val lstLifeInsuranceCode = command.stream().map(item -> item.getLifeInsuranceCode()).collect(Collectors.toList());
        List<EarthquakeInsurance> lstEarthquakeInsurance = repository.getEarthquakeByLstLifeInsuranceCode(cid, lstLifeInsuranceCode);
        if (lstEarthquakeInsurance.size() > 0) {
            val lstEarthquakeInsuranceCode = lstEarthquakeInsurance.stream().map(item -> item.getEarthquakeInsuranceCode()).collect(Collectors.toList());
            List<String> lstCodeAdd = new ArrayList<>();;
            lstCodeAdd.addAll(lstLifeInsuranceCode);
            for(int i = 0 ; i < lstLifeInsuranceCode.size();i++){
                for(int j = 0; j < lstEarthquakeInsuranceCode.size(); j++){
                    if(lstLifeInsuranceCode.get(i).equalsIgnoreCase(lstEarthquakeInsuranceCode.get(j).toString())){
                        lstCodeAdd.remove(lstLifeInsuranceCode.get(i));
                    }
                }
            }
            if(lstCodeAdd.size() > 0){
                List<LifeInsurance> lstDataUpdate = repository.getLifeInsurancedByLstEarthquakeInsuranceCode(cid, lstCodeAdd);
                repository.copyAddEarthQuakeInsu(lstDataUpdate);
            }

        } else {
            repository.copyAddEarthQuakeInsu(lstLifeInsurance);
        }
    }
}
