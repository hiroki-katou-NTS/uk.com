package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkpcounter;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterCategory;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterRepo;
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
    I18NResourcesForUK ukResource;

    public List<WorkplaceCounterCategoryDto> findById() {

        Optional<WorkplaceCounter> workplaceCounter = repository.get(AppContexts.user().companyId());
        List<EnumConstant> listEnum = EnumAdaptor.convertToValueNameList(WorkplaceCounterCategory.class, ukResource);
        return WorkplaceCounterCategoryDto.setData(listEnum,workplaceCounter);
    }
}

