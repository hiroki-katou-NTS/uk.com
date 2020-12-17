package nts.uk.ctx.at.schedule.dom.budget.premium;


import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;

@Stateless
public class HistPersonCostCalculationDomainService implements IHistPersonCostCalculationDomainService {

    @Inject
    private PersonCostCalculationRepository personCostCalculationRepository;

    public void createHistPersonCostCalculation(GeneralDate date) {
        // Get all list history

        val companyId = AppContexts.user().companyId();
        val listPersonCostCalculation = this.personCostCalculationRepository.getHistPersonCostCalculation(companyId);
        // List history
        HistPersonCostCalculation listHist = new HistPersonCostCalculation(companyId, new ArrayList<>());
        if (listPersonCostCalculation.isPresent()) {
            listHist = listPersonCostCalculation.get();
        }
        DatePeriod datePeriod = new DatePeriod(date, GeneralDate.max());
        // Item need to add
        DateHistoryItem itemToBeAdded = DateHistoryItem.createNewHistory(datePeriod);
        // Add into old list
        listHist.add(itemToBeAdded);
        //  Item to be update.
        val itemToBeUpdated = listHist.immediatelyBefore(itemToBeAdded);

        // Update pre hist
        itemToBeUpdated.ifPresent(dateHistoryItem ->
                personCostCalculationRepository.updateHistPersonCl(HistPersonCostCalculation.toDomain(companyId, Collections.singletonList(dateHistoryItem))));
        // Add new item
        personCostCalculationRepository.createHistPersonCl(HistPersonCostCalculation.toDomain(companyId, Collections.singletonList(itemToBeAdded)));

    }
}
