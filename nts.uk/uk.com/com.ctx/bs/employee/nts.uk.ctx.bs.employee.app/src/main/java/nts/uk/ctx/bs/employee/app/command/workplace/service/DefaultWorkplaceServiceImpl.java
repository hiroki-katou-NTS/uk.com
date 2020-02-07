/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.policy.HistoryPolicy;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class DefaultWorkplaceServiceImpl.
 */
@Stateless
public class DefaultWorkplaceServiceImpl implements WorkplaceService {

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
        DatePeriod period = workplace.items().get(ELEMENT_FIRST).span();
        workplace.items().get(ELEMENT_FIRST).changeSpan(period.newSpan(period.start(), endĐate));
        this.workplaceRepo.update(workplace);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.service.WorkplaceService#
     * removeWkpHistory(java.lang.String, java.lang.String,
     * nts.arc.time.GeneralDate)
     */
    @Override
    public void removeWkpHistory(String companyId, String wkpId, GeneralDate startDate) {
        Workplace workplace = this.workplaceRepo.findByWorkplaceId(companyId, wkpId).get();
        
        List<String> lstHistIdRemoved = new ArrayList<>();
        workplace.items().forEach(wkpHistory -> {
            if (wkpHistory.span().start().after(startDate)) {
                // remove workplace history and workplace infor
                this.workplaceRepo.removeWkpHistory(companyId, wkpId, wkpHistory.identifier());
                
                // save history removed.
                lstHistIdRemoved.add(wkpHistory.identifier());
            }
        });
        
        // delete WorkplaceHistory
        workplace.items().removeIf(item -> lstHistIdRemoved.contains(item.identifier()));
        
        // update end date of workplace history latest current
        this.updatePreviousHistory(companyId, workplace.getWkpHistoryLatest().identifier(), HistoryPolicy.getMaxDate());
    }

}
