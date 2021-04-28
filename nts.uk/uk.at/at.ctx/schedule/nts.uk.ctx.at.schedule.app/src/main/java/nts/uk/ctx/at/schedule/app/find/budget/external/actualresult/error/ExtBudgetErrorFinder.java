/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.error;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.error.dto.ExternalBudgetErrorDto;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetError;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetErrorRepository;

/**
 * The Class ExtBudgetErrorFinder.
 */
@Stateless
public class ExtBudgetErrorFinder {
    
    /** The error repo. */
    @Inject
    private ExternalBudgetErrorRepository errorRepo;
    
    /**
     * Find errors.
     *
     * @param executeId the execute id
     * @return the list
     */
    public List<ExternalBudgetErrorDto> findErrors(String executeId) {
        List<ExternalBudgetError> lstError = this.errorRepo.findByExecutionId(executeId);
        return lstError.stream()
                .map(domain -> {
                    ExternalBudgetErrorDto dto = new ExternalBudgetErrorDto();
                    domain.saveToMemento(dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
