/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class WkpConfigServiceImpl.
 */
@Stateless
public class WkpConfigServiceImpl implements WkpConfigService {

    /** The workplace config repository. */
    @Inject
    private WorkplaceConfigRepository workplaceConfigRepository;

    /** The wkp config info repo. */
    @Inject
    private WorkplaceConfigInfoRepository wkpConfigInfoRepo;

    /** The workplace repo. */
    @Inject
    private WorkplaceRepository workplaceRepo;

    /** The workplace info repo. */
    @Inject
    private WorkplaceInfoRepository workplaceInfoRepo;

    /** The Constant ELEMENT_FIRST. */
    private static final Integer ELEMENT_FIRST = 0;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.ctx.bs.employee.dom.workplace.config.service.WkpConfigService#
     * updatePrevHistory(java.lang.String, nts.arc.time.GeneralDate)
     */
    @Override
    public void updatePrevHistory(String companyId, String prevHistId, GeneralDate endĐate) {
        Optional<WorkplaceConfig> optional = workplaceConfigRepository.findByHistId(companyId, prevHistId);
        if (!optional.isPresent()) {
            throw new RuntimeException(String.format("History id %s didn't existed.", prevHistId));
        }
        WorkplaceConfig wkpConfig = optional.get();
        // set end date of previous history
        DatePeriod period = wkpConfig.getWkpConfigHistory().get(ELEMENT_FIRST).getPeriod();
        wkpConfig.getWkpConfigHistory().get(ELEMENT_FIRST).setPeriod(period.newSpan(period.start(), endĐate));
        workplaceConfigRepository.update(wkpConfig);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.ctx.bs.employee.dom.workplace.config.service.WkpConfigService#
     * updateWkpHistoryIfNeed(java.lang.String,
     * nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory,
     * nts.arc.time.GeneralDate)
     */
    @Override
    public void updateWkpHistoryIfNeed(String companyId, WorkplaceConfigHistory latestWkpConfigHist,
            GeneralDate newHistStartDate) {
        String historyId = latestWkpConfigHist.getHistoryId();
        Optional<WorkplaceConfigInfo> optionalWkpConfigInfo = this.wkpConfigInfoRepo.find(companyId, historyId);
        if (!optionalWkpConfigInfo.isPresent()) {
            return;
        }
        // find all workplace by historyId
        List<String> lstWkpId = optionalWkpConfigInfo.get().getLstWkpHierarchy().stream()
                .map(item -> item.getWorkplaceId()).collect(Collectors.toList());

        // check date of workplace
        List<Workplace> lstWorkplace = this.workplaceRepo.findByWkpIds(lstWkpId);
        
        lstWorkplace.forEach(workplace -> {
            this.progress(workplace, latestWkpConfigHist.getPeriod().start(), newHistStartDate);
        });
    }

    /**
     * Progress.
     *
     * @param workplace
     *            the workplace
     * @param startDateHistCurrent
     *            the start date hist current
     * @param newStartDateHist
     *            the new start date hist
     */
    private void progress(Workplace workplace, GeneralDate startDateHistCurrent, GeneralDate newStartDateHist) {
        List<WorkplaceHistory> lstNewWkpHistory = new ArrayList<>();
        for (WorkplaceHistory wkpHistory : workplace.getWorkplaceHistory()) {
            GeneralDate startDateWkpHist = wkpHistory.getPeriod().start();
            // not equal
            if (!startDateWkpHist.equals(startDateHistCurrent)) {
                continue;
            }
            // find date range need to remove
            List<String> lstHistoryIdRemove = this.findExistedDateInRange(workplace.getWorkplaceHistory(),
                    newStartDateHist, startDateWkpHist);
            if (!CollectionUtil.isEmpty(lstHistoryIdRemove)) {
                // remove workplace infor in date range
                this.removeWorkplaceInfor(workplace.getCompanyId(), workplace.getWorkplaceId(), lstHistoryIdRemove);
            }
            startDateWkpHist = newStartDateHist;

            lstNewWkpHistory = this.subListWkpHistory(workplace.getWorkplaceHistory(), newStartDateHist);
            lstNewWkpHistory.add(wkpHistory);
            break;
        }
        // empty list workplace history
        workplace.getWorkplaceHistory().clear();

        // add all new list workplace history
        workplace.getWorkplaceHistory().addAll(lstNewWkpHistory);

        // update workplace
        this.workplaceRepo.update(workplace);
    }

    /**
     * Find existed date in range.
     *
     * @param lstWkpHistory
     *            the lst wkp history
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the list
     */
    private List<String> findExistedDateInRange(List<WorkplaceHistory> lstWkpHistory, GeneralDate startDate,
            GeneralDate endDate) {
        return lstWkpHistory.stream()
                .filter(item -> item.getPeriod().start().afterOrEquals(startDate)
                        && item.getPeriod().start().beforeOrEquals(endDate))
                .map(item -> item.getHistoryId())
                .collect(Collectors.toList());
    }

    /**
     * Removes the workplace infor.
     *
     * @param companyId
     *            the company id
     * @param workplaceId
     *            the workplace id
     * @param lstHistoryIdRemove
     *            the lst history id remove
     */
    private void removeWorkplaceInfor(String companyId, String workplaceId, List<String> lstHistoryIdRemove) {
        lstHistoryIdRemove.forEach(historyId -> {
            this.workplaceInfoRepo.remove(companyId, workplaceId, historyId);
        });
    }

    /**
     * Sub list wkp history.
     *
     * @param lstWorkplaceHistory
     *            the lst workplace history
     * @param newStartDateHist
     *            the new start date hist
     * @return the list
     */
    private List<WorkplaceHistory> subListWkpHistory(List<WorkplaceHistory> lstWorkplaceHistory,
            GeneralDate newStartDateHist) {
        List<WorkplaceHistory> subListWkpHistory = lstWorkplaceHistory.stream()
                .filter(item -> item.getPeriod().start().before(newStartDateHist)).collect(Collectors.toList());

        if (CollectionUtil.isEmpty(subListWkpHistory)) {
            return new ArrayList<>();
        }
        // set previous a day for end date
        DatePeriod period = subListWkpHistory.get(0).getPeriod();
        subListWkpHistory.get(0).setPeriod(period.newSpan(period.start(), newStartDateHist.addDays(-1)));

        return subListWkpHistory;
    }
}
