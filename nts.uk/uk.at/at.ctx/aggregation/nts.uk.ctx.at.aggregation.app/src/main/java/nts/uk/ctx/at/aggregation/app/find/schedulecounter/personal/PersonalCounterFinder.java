package nts.uk.ctx.at.aggregation.app.find.schedulecounter.personal;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.PersonalCounterRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * スケジュール個人計情報を取得する
 */
@Stateless
public class PersonalCounterFinder {
    @Inject
    private PersonalCounterRepo repository;

    @Inject
    private TimesNumberCounterSelectionRepo timesNumberRepo;

    @Inject
    I18NResourcesForUK ukResource;

    public List<PersonalCounterCategoryDto> findById() {

        //check setting person1
        Optional<TimesNumberCounterSelection> timesNumber1 = timesNumberRepo.get(AppContexts.user().companyId(), TimesNumberCounterType.PERSON_1);

        //check setting person2
        Optional<TimesNumberCounterSelection> timesNumber2 = timesNumberRepo.get(AppContexts.user().companyId(), TimesNumberCounterType.PERSON_2);

        //check setting person3
        Optional<TimesNumberCounterSelection> timesNumber3 = timesNumberRepo.get(AppContexts.user().companyId(), TimesNumberCounterType.PERSON_3);

        Optional<PersonalCounter> personalCounter = repository.get(AppContexts.user().companyId());
        List<EnumConstant> listEnum = EnumAdaptor.convertToValueNameList(PersonalCounterCategory.class, ukResource);

        return PersonalCounterCategoryDto.setData(listEnum,personalCounter,timesNumber1.isPresent(),timesNumber2.isPresent(),timesNumber3.isPresent());
    }
}

