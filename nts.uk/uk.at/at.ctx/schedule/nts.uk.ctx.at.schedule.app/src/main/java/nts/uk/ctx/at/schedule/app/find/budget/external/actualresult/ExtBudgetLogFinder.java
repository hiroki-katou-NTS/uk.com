/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExternalBudgetQuery;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLogRepository;
import nts.uk.shr.com.context.AppContexts;

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
        String companyId = AppContexts.user().companyId();
        String employeeIdLogin = AppContexts.user().employeeId();
        
        // find external budget setting
        Map<String, String> mapBudget = this.externalBudgetRepo.findAll(companyId).stream()
                .collect(Collectors.toMap(item -> item.getExternalBudgetCd().v(),
                        item -> item.getExternalBudgetName().v()));
        
        List<ExternalBudgetLog> lstLog = this.extBudgetLogRepo.findExternalBudgetLog(employeeIdLogin, query.getStartDate(), query.getEndDate(),
                query.getListState());
        
        // check has data ?
        if (CollectionUtil.isEmpty(lstLog)) {
            throw new BusinessException("Msg_166");
        }
        return lstLog.stream()
                .map(domain -> {
                    ExternalBudgetLogDto dto = new ExternalBudgetLogDto();
                    domain.saveToMemento(dto);
                    
                    // set external budget name
                    dto.extBudgetName = mapBudget.get(domain.getExtBudgetCode().v());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
