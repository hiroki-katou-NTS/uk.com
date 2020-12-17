package nts.uk.ctx.at.schedule.app.find.budget.premium;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PersonCostCalculationSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumItemDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumSetDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.ShortAttendanceItemDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.*;
import nts.uk.ctx.at.schedule.dom.budget.premium.language.PremiumItemLanguage;
import nts.uk.ctx.at.schedule.dom.budget.premium.language.PremiumItemLanguageRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceNamePriniumAdapter;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceNamePriniumDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceTypePriServiceDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceTypePrimiumAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Doan Duy Hung
 * update by chinh.hm
 */

@Stateless
@Transactional
public class PersonCostCalculationFinder {

    @Inject
    private PersonCostCalculationRepository personCostCalculationRepository;

    @Inject
    private PremiumItemRepository premiumItemRepository;

    @Inject
    private AttendanceTypePrimiumAdapter atType;
    @Inject
    private AttendanceNamePriniumAdapter atName;

    @Inject
    private PremiumItemLanguageRepository premiumItemLanguageRepository;

    /**
     * get all Person Cost Calculation by company ID
     *
     * @return list Person Cost Calculation by company ID
     */
    public List<PersonCostCalculationSettingDto> findPersonCostCalculationByCompanyID() {
        String companyID = AppContexts.user().companyId();
        return this.personCostCalculationRepository.findByCompanyID(companyID)
                .stream()
                .map(x -> convertToSimpleDto(x))
                .collect(Collectors.toList());
    }

    /**
     * get List Premium Item  by company ID and by UseAttribute.
     *
     * @return List Premium Item  by company ID
     */
    public List<PremiumItemDto> findPremiumItemByCompanyID() {
        String companyID = AppContexts.user().companyId();
        return this.premiumItemRepository.findAllIsUse(companyID)
                .stream()
                .map(x -> new PremiumItemDto(
                        companyID,
                        x.getDisplayNumber(),
                        x.getName().v(),
                        x.getUseAtr().value))
                .collect(Collectors.toList());
    }

    public List<PremiumItemDto> findWorkTypeLanguage(String langId) {
        // company id
        String companyId = AppContexts.user().companyId();
        List<PremiumItemLanguage> listPremiumItem = premiumItemLanguageRepository.findByCIdAndLangId(companyId, langId);
        return listPremiumItem.stream().map(x -> {
            return PremiumItemDto.fromDomainPremiumItemLanguage(x);
        }).collect(Collectors.toList());
    }

    public PersonCostCalculationSettingDto findByHistoryID(String historyID) {
        String companyID = AppContexts.user().companyId();
        return this.personCostCalculationRepository.findItemByHistoryID(companyID, historyID)
                .map(x -> convertToDto(x)).orElse(null);
    }

    private PersonCostCalculationSettingDto convertToSimpleDto(PersonCostCalculation personCostCalculation) {
        return new PersonCostCalculationSettingDto(
                personCostCalculation.getCompanyID(),
                personCostCalculation.getHistoryID(),
                personCostCalculation.getStartDate(),
                personCostCalculation.getEndDate(),
                personCostCalculation.getUnitPrice().value,
                personCostCalculation.getMemo().v(),
                Collections.emptyList());
    }

    /**
     * convert PersonCostCalculation to PersonCostCalculationSettingDto
     *
     * @param personCostCalculation PersonCostCalculation Object
     * @return PersonCostCalculationSettingDto Object
     */
    private PersonCostCalculationSettingDto convertToDto(PersonCostCalculation personCostCalculation) {
        return new PersonCostCalculationSettingDto(
                personCostCalculation.getCompanyID(),
                personCostCalculation.getHistoryID(),
                personCostCalculation.getStartDate(),
                personCostCalculation.getEndDate(),
                personCostCalculation.getUnitPrice().value,
                personCostCalculation.getMemo().v(),
                personCostCalculation.getPremiumSettings().stream().map(x -> toPremiumSetDto(x)).collect(Collectors.toList()));
    }

    /**
     * convert PremiumSetting to PremiumSetDto
     *
     * @param premiumSetting PremiumSetting Object
     * @return PremiumSetDto Object
     */
    private PremiumSetDto toPremiumSetDto(PremiumSetting premiumSetting) {
        return new PremiumSetDto(
                premiumSetting.getCompanyID(),
                premiumSetting.getHistoryID(),
                premiumSetting.getDisplayNumber(),
                premiumSetting.getRate().v(),
                premiumSetting.getName().v(),
                premiumSetting.getUseAtr().value,
                premiumSetting.getAttendanceItems().stream().map(x -> new ShortAttendanceItemDto(x, x.toString())).collect(Collectors.toList()));
    }

    /**
     * get attendance at screen use
     *
     * @param screenUseAtr
     * @return
     */
    public List<AttendanceTypePriServiceDto> atTypes(int screenUseAtr) {
        String companyID = AppContexts.user().companyId();
        List<AttendanceTypePriServiceDto> data = atType.getItemByScreenUseAtr(companyID, screenUseAtr);
        return data;
    }

    public List<AttendanceNamePriniumDto> atNames(List<Integer> dailyAttendanceItemIds) {
        List<AttendanceNamePriniumDto> data = atName.getDailyAttendanceItemName(dailyAttendanceItemIds);
        return data;
    }

    /**
     * Get 人件費計算設定の履歴
     */

    public HistPersonCostCalculation getHistPersonCostCalculations() {
        val cid = AppContexts.user().companyId();
        val listItem = this.personCostCalculationRepository.findByCompanyID(cid);
        if (listItem.isEmpty()) {
            throw new BusinessException("Msg_2027");
        }
        return new HistPersonCostCalculation(
                cid,
                listItem.stream().map(e ->
                        new DateHistoryItem(e.getHistoryID(), new DatePeriod(e.getStartDate(), e.getEndDate())
                        )).collect(Collectors.toList())
        );

    }
}
