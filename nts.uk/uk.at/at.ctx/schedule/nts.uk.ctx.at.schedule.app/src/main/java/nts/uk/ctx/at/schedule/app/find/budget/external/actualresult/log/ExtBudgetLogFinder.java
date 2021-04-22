/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.log;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.log.dto.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.log.dto.ExternalBudgetQuery;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExternalBudgetLogRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ExtBudgetLogFinder.
 */
@Stateless
public class ExtBudgetLogFinder {
    
    /** The external budget repo. */
    @Inject
    private ExternalBudgetRepository externalBudgetRepo;
    
    /** The ext budget log repo. */
    @Inject
    private ExternalBudgetLogRepository extBudgetLogRepo;
    
    /**
     * Find external budget log.
     *
     * @param query the query
     * @return the list
     */
    public List<ExternalBudgetLogDto> findExternalBudgetLog(ExternalBudgetQuery query) {
        // check choose at least state completion ?
        if (CollectionUtil.isEmpty(query.getListState())) {
            throw new BusinessException("Msg_166");
        }
        LoginUserContext user = AppContexts.user();
        // find external budget setting
        Map<String, String> mapBudget = this.externalBudgetRepo.findAll(user.companyId()).stream()
                .collect(Collectors.toMap(item -> item.getExternalBudgetCd().v(),
                        item -> item.getExternalBudgetName().v()));
        List<ExternalBudgetLog> lstLog = this.extBudgetLogRepo.findExternalBudgetLog(user.employeeId(),
                query.getStartDate(), query.getEndDate(), query.getListState());
        return lstLog.stream()
                .map(domain -> {
                    ExternalBudgetLogDto dto = new ExternalBudgetLogDto();
                    domain.saveToMemento(dto);
                    
                    // set external budget name
                    String extBudgetName = mapBudget.get(domain.getExtBudgetCode().v());
                    if (extBudgetName == null) {
                        extBudgetName = "未設定";
                    }
                    dto.extBudgetName = extBudgetName;
                    return dto;
                }).collect(Collectors.toList());
    }
}
