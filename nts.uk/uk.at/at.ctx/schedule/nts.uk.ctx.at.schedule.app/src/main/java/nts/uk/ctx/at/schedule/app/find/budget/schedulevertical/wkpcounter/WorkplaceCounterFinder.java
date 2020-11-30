package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkpcounter;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterCategory;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumber;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumberRepo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
/**
 * スケジュール職場計情報を取得する
 */
@Stateless
public class WorkplaceCounterFinder {
    @Inject
    private WorkplaceCounterRepo repository;

    @Inject
    private WorkplaceCounterLaborCostAndTimeRepo laborCostAndTimeRepo;

    @Inject
    private WorkplaceCounterTimeZonePeopleNumberRepo timeZoneRepo;

    @Inject
    private TimesNumberCounterSelectionRepo timesNumberRepo;

    @Inject
    I18NResourcesForUK ukResource;

    public List<WorkplaceCounterCategoryDto> findById() {

        //check setting CostAndTime
        Optional<WorkplaceCounterLaborCostAndTime> wkpLaborCostAndTime = laborCostAndTimeRepo.get(AppContexts.user().companyId());

        //check setting timesNumber
        Optional<TimesNumberCounterSelection> timesNumber = timesNumberRepo.get(AppContexts.user().companyId(), TimesNumberCounterType.WORKPLACE);

        //check setting timeZone
        Optional<WorkplaceCounterTimeZonePeopleNumber> timeZonePeopleNumber = timeZoneRepo.get(AppContexts.user().companyId());

        Optional<WorkplaceCounter> workplaceCounter = repository.get(AppContexts.user().companyId());
        List<EnumConstant> listEnum = EnumAdaptor.convertToValueNameList(WorkplaceCounterCategory.class, ukResource);
        return WorkplaceCounterCategoryDto.setData(listEnum,workplaceCounter,wkpLaborCostAndTime.isPresent(),timesNumber.isPresent(),timeZonePeopleNumber.isPresent());
    }
}

