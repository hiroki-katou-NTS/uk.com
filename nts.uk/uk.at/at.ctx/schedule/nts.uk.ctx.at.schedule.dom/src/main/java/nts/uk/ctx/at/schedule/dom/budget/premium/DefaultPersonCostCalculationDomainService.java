package nts.uk.ctx.at.schedule.dom.budget.premium;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Doan Duy Hung
 */
@Stateless
public class DefaultPersonCostCalculationDomainService implements PersonCostCalculationDomainService {

    @Inject
    private PersonCostCalculationRepository personCostCalculationRepository;


    @Override
    public void deletePersonCostCalculation(String companyId, String historyId) {


        //Get list old  by companyId
        val listOldHistPersonCostCal = personCostCalculationRepository.getHistPersonCostCalculation(companyId);
        val listOldPersonCostCal = personCostCalculationRepository.getPersonCost(companyId, historyId);

        if (!listOldHistPersonCostCal.isPresent() || !listOldPersonCostCal.isPresent()) {
            throw new BusinessException("");
        }
        // Get item delete
        Optional<DateHistoryItem> optionalHisItem = listOldHistPersonCostCal.get().getHistoryItems().stream()
                .filter(x -> x.identifier().equals(historyId)).findFirst();
        if (!optionalHisItem.isPresent()) {

            throw new BusinessException("");
        }
        // Remove history in list
        listOldHistPersonCostCal.get().remove(optionalHisItem.get());

        // Delete history
        this.personCostCalculationRepository.delete(companyId, historyId);

        // Update before history

        this.personCostCalculationRepository.update(listOldPersonCostCal.get().getPersonCostCalculation(), listOldHistPersonCostCal.get());
    }

    @Override
    public void updateHistPersonCalculation(PersonCostCalculation domain, String histotyId,  GeneralDate generalDate) {
        val companyId = AppContexts.user().companyId();
        // List all Hist
        val listHistPersonCostCal = this.personCostCalculationRepository.getHistPersonCostCalculation(companyId);

        // Create list empty.
        HistPersonCostCalculation listHist = new HistPersonCostCalculation(companyId, new ArrayList<>());
        if (listHistPersonCostCal.isPresent()) {
            listHist = listHistPersonCostCal.get();
        }
        // Get item update
        Optional<DateHistoryItem> optionalHisItem = listHist.items().stream()
                .filter(x -> x.identifier().equals(histotyId)).findFirst();
        if (!optionalHisItem.isPresent()) {

            throw new BusinessException("");

        }
        val period = new DatePeriod(generalDate,GeneralDate.max());
        //Update item
        listHist.changeSpan(optionalHisItem.get(), period);
        // get item before
        val itemBefore = listHist.immediatelyBefore(optionalHisItem.get());
        val listUpdate = new ArrayList<DateHistoryItem>();
        listUpdate.add(optionalHisItem.get());
        itemBefore.ifPresent(listUpdate::add);

        personCostCalculationRepository.update(domain, new HistPersonCostCalculation(companyId, listUpdate));
    }

    @Override
    public void registerLaborCalculationSetting(PersonCostCalculation domain, GeneralDate date) {
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
        val histId = itemToBeAdded.identifier();
        // Add into old list
        listHist.add(itemToBeAdded);
        //  Item to be update.
        val itemToBeUpdated = listHist.immediatelyBefore(itemToBeAdded);
        // Update pre hist
        itemToBeUpdated.ifPresent(dateHistoryItem ->
                personCostCalculationRepository.updateHistPersonCl(HistPersonCostCalculation.toDomain(companyId, Collections.singletonList(dateHistoryItem))));
        // Add new item
        personCostCalculationRepository.createHistPersonCl(domain, itemToBeAdded.start(), itemToBeAdded.end(), histId);

    }
}
