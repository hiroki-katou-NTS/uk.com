/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class WorkplaceServiceImpl.
 */
@Stateless
public class WorkplaceServiceImpl implements WorkplaceService {

    /** The workplace repo. */
    @Inject
    private WorkplaceRepository workplaceRepo;

    /** The Constant ELEMENT_FIRST. */
    private static final Integer ELEMENT_FIRST = 0;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.service.WorkplaceService#
     * updatePreviousHistory(java.lang.String, java.lang.String,
     * nts.arc.time.GeneralDate)
     */
    @Override
    public void updatePreviousHistory(String companyId, String prevHistId, GeneralDate endĐate) {
        Optional<Workplace> optional = this.workplaceRepo.findByHistoryId(companyId, prevHistId);
        if (!optional.isPresent()) {
            throw new RuntimeException(String.format("History id %s didn't existed.", prevHistId));
        }
        Workplace workplace = optional.get();
        // set end date of previous history
        DatePeriod period = workplace.getWorkplaceHistory().get(ELEMENT_FIRST).getPeriod();
        workplace.getWorkplaceHistory().get(ELEMENT_FIRST).setPeriod(period.newSpan(period.start(), endĐate));
        this.workplaceRepo.update(workplace);
    }

}
