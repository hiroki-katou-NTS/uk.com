package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.HistAnPerCost;

/**
 * @author Doan Duy Hung
 */

public interface PersonCostCalculationRepository {

    /**
     * insert PersonCostCalculation
     *
     * @param personCostCalculation PersonCostCalculation
     */
     void add(PersonCostCalculation personCostCalculation);

    /**
     * get list PersonCostCalculation by company ID
     *
     * @param companyID company ID
     * @return list PersonCostCalculation by company ID
     */
     List<PersonCostCalculation> findByCompanyID(String companyID);

    /**
     * get single PersonCostCalculation by company ID and history ID
     *
     * @param companyID company ID
     * @return single PersonCostCalculation by company ID and history ID
     */
     Optional<PersonCostCalculation> findItemByDate(String companyID, GeneralDate startDate);

    /**
     * get single PersonCostCalculation by company ID and history ID
     *
     * @param companyID company ID
     * @param historyID history ID
     * @return single PersonCostCalculation by company ID and history ID
     */
     Optional<PersonCostCalculation> findItemByHistoryID(String companyID, String historyID);

    /**
     * get single PersonCostCalculation before input date by company ID
     *
     * @param companyID company ID
     * @return single PersonCostCalculation before input date by company ID
     */
     Optional<PersonCostCalculation> findItemBefore(String companyID, GeneralDate startDate);

    /**
     * get single PersonCostCalculation after input date by company ID
     *
     * @param companyID company ID
     * @return single PersonCostCalculation after input date by company ID
     */
     Optional<PersonCostCalculation> findItemAfter(String companyID, GeneralDate startDate);


     void update(PersonCostCalculation personCostCalculation);

     void delete(String companyId, String historyId);


     List<PersonCostCalculation> findByCompanyIDAndDisplayNumber(String companyID, GeneralDate date);

     List<PersonCostCalculation> findByCompanyIDAndDisplayNumberNotFull(String companyID, DatePeriod date, List<Integer> itemNos);

     List<PremiumSetting> findPremiumSettingBy(String companyID, GeneralDate date);


     Optional<HistPersonCostCalculation> getHistPersonCostCalculation(String cid);


     Optional<PersonCostCalculation> getPersonCost(String cid, String histId);

     void createHistPersonCl(PersonCostCalculation domain, GeneralDate startDate, GeneralDate endDate, String histId);

     void updateHistPersonCl(HistPersonCostCalculation domain);

     void update(PersonCostCalculation domain, HistPersonCostCalculation domainHist);

     List<PremiumSetting> getPersonCostByListHistId(String cid, List<String> histId);

    HistAnPerCost getHistAnPerCost(String companyID);
}