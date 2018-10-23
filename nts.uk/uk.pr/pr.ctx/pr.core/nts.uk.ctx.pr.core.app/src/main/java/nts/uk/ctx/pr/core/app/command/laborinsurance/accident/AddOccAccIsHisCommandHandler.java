package nts.uk.ctx.pr.core.app.command.laborinsurance.accident;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.laborinsurance.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Transactional
public class
AddOccAccIsHisCommandHandler extends CommandHandler<AccInsurHisCommand> {

    @Inject
    private OccAccIsHisRepository repository;

    @Inject
    private OccAccIsPrRateRepository occAccIsPrRateRepository;

    @Inject
    private OccAccInsurBusRepository occAccInsurBusRepository;

    @Override
    protected void handle(CommandHandlerContext<AccInsurHisCommand> commandHandlerContext) {
        AccInsurHisCommand addCommand = commandHandlerContext.getCommand();
        String cId = AppContexts.user().companyId();
        String newHistID = IdentifierUtil.randomUniqueId();
        Optional<OccAccInsurBus> occAccInsurBus = occAccInsurBusRepository.getOccAccInsurBus(cId);
        List<OccAccInsurBusiBurdenRatio> temp = new ArrayList<OccAccInsurBusiBurdenRatio>();

        for (NameOfEachBusiness nameOfEachBusiness: occAccInsurBus.get().getEachBusiness() ) {
            temp.add(new OccAccInsurBusiBurdenRatio(nameOfEachBusiness.getOccAccInsurBusNo(), EnumAdaptor.valueOf(nameOfEachBusiness.getToUse(),InsuPremiumFractionClassification.class),new InsuranceRate(new BigDecimal(0))));
        }
        occAccIsPrRateRepository.add(temp,newHistID);
        repository.add(new YearMonthHistoryItem(newHistID, new YearMonthPeriod(new YearMonth(addCommand.getStartMonthYear()),new YearMonth(999912))), cId);
    }

}
