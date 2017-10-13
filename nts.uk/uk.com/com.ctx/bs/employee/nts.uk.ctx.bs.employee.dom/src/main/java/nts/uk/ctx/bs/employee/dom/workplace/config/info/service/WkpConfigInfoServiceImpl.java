/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.bs.employee.dom.workplace.CreateWorkpceType;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;

/**
 * The Class WkpConfigInfoServiceImpl.
 */
@Stateless
public class WkpConfigInfoServiceImpl implements WkpConfigInfoService {

    /** The wkp config info repo. */
    @Inject
    WorkplaceConfigInfoRepository wkpConfigInfoRepo;

    /** The Constant FIRST. */
    private static final Integer FIRST = 0;

    /** The Constant HIERARCHY_LENGTH. */
    private static final Integer HIERARCHY_LENGTH = 3;

    /** The Constant EQUAL_VALUE. */
    private static final Integer EQUAL_VALUE = 0;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.service.
     * WkpConfigInfoService#copyWkpConfigInfoHist(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void copyWkpConfigInfoHist(String companyId, String latestHistIdCurrent, String newHistId) {
        // get all WorkplaceConfigInfo of old hist
        Optional<WorkplaceConfigInfo> optional = wkpConfigInfoRepo.find(companyId, latestHistIdCurrent);
        if (!optional.isPresent()) {
            return;
        }
        WorkplaceConfigInfo wkpConfigInfo = optional.get();

        // convert new list
        WorkplaceConfigInfo newWorkplace = wkpConfigInfo.cloneWithHistId(newHistId);

        // add list with new historyId
        wkpConfigInfoRepo.add(newWorkplace);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.service.
     * WkpConfigInfoService#updateWkpHierarchy(java.lang.String,
     * java.lang.String, java.lang.String,
     * nts.uk.ctx.bs.employee.dom.workplace.CreateWorkpceType)
     */
    @Override
    public void updateWkpHierarchy(String historyId, String wkpIdSelected, Workplace newWorkplace,
            CreateWorkpceType createType) {
        Optional<WorkplaceConfigInfo> optional = wkpConfigInfoRepo.find(newWorkplace.getCompanyId(),
                historyId);
        if (!optional.isPresent()) {
            throw new RuntimeException(String.format("Didn't have workplace config infor %s", historyId));
        }
        WorkplaceConfigInfo wkpConfigInfo = optional.get();

        Optional<WorkplaceHierarchy> optionalWkpHierarchySelected = wkpConfigInfo.getLstWkpHierarchy().stream()
                .filter(item -> item.getWorkplaceId().equals(wkpIdSelected))
                .findFirst();
        if (!optionalWkpHierarchySelected.isPresent()) {
            throw new RuntimeException(String.format("Didn't have workplace %s", wkpIdSelected));
        }

        WorkplaceHierarchy wkpHierarchySelected = optionalWkpHierarchySelected.get();
        
        // create hierarchy at child
        if (createType == CreateWorkpceType.CREATE_AT_CHILD) {
            this.updateHierarchyChild(wkpConfigInfo.getLstWkpHierarchy(), wkpHierarchySelected,
                    newWorkplace.getWorkplaceId());
        } else {
            this.process(wkpConfigInfo.getLstWkpHierarchy(), wkpHierarchySelected, createType,
                    newWorkplace.getWorkplaceId());
        }
        
        // update workplace config infor
        this.wkpConfigInfoRepo.update(wkpConfigInfo);
    }

    /**
     * Process.
     *
     * @param lstWkpHierarchy
     *            the lst wkp hierarchy
     * @param wkpHierarchySelected
     *            the wkp hierarchy selected
     * @param createType
     *            the create type
     * @param newWkpId
     *            the new wkp id
     */
    private void process(List<WorkplaceHierarchy> lstWkpHierarchy, WorkplaceHierarchy wkpHierarchySelected,
            CreateWorkpceType createType, String newWkpId) {

        String hierarchyCdSelected = wkpHierarchySelected.getHierarchyCode().v();

        String hierarchyParent = hierarchyCdSelected.substring(FIRST, hierarchyCdSelected.length() - HIERARCHY_LENGTH);

        WorkplaceHierarchy newWkpHierarchy = null;

        for (WorkplaceHierarchy wkpHierarchy : lstWkpHierarchy) {

            String hierarchyCd = wkpHierarchy.getHierarchyCode().v();

            // except hierarchy not relation
            if (!hierarchyCd.startsWith(hierarchyParent)) {
                continue;
            }
            // update hierarchyCd child
            if (hierarchyCd.length() > hierarchyCdSelected.length()) {
                String newHierarchyCd = hierarchyCd.replace(hierarchyCdSelected,
                        this.getNewHierarchyCd(hierarchyCdSelected));
                wkpHierarchy.setHierarchyCode(new HierarchyCode(newHierarchyCd));
                continue;
            }

            // update hierarchyCd siblings
            WorkplaceHierarchy wkpHierarchyResult = this.updateHierarchySiblings(wkpHierarchy, hierarchyCdSelected,
                    createType, newWkpId);
            if (wkpHierarchyResult != wkpHierarchy) {
                newWkpHierarchy = wkpHierarchyResult;
            }
        }

        // add new workplace hierarchy
        lstWkpHierarchy.add(newWkpHierarchy);
    }

    /**
     * Update hierarchy child.
     *
     * @param lstWkpHierarchy
     *            the lst wkp hierarchy
     * @param wkpHierarchySelected
     *            the wkp hierarchy selected
     * @param newWkpId
     *            the new wkp id
     */
    private void updateHierarchyChild(List<WorkplaceHierarchy> lstWkpHierarchy, WorkplaceHierarchy wkpHierarchySelected,
            String newWkpId) {
        String hierarchyCdSelected = wkpHierarchySelected.getHierarchyCode().v();

        WorkplaceHierarchy wkpHierarchyLast = lstWkpHierarchy.stream()
                .filter(wkpHierarchy -> wkpHierarchy.getHierarchyCode().v().startsWith(hierarchyCdSelected))
                .sorted((item1, item2) -> item2.getHierarchyCode().v().compareTo(item1.getHierarchyCode().v()))
                .findFirst()
                .orElse(null);

        String newHierarchyLast = hierarchyCdSelected;
        if (wkpHierarchyLast != null) {
            newHierarchyLast = wkpHierarchyLast.getHierarchyCode().v();
        }
        WorkplaceHierarchy newWkpHierarchy = WorkplaceHierarchy.newInstance(newWkpId,
                this.getNewHierarchyCd(newHierarchyLast));
        lstWkpHierarchy.add(newWkpHierarchy);
    }

    /**
     * Update hierarchy siblings.
     *
     * @param wkpHierarchy
     *            the wkp hierarchy
     * @param hierarchyCdSelected
     *            the hierarchy cd selected
     * @param createType
     *            the create type
     * @param newWkpId
     *            the new wkp id
     * @return the workplace hierarchy
     */
    private WorkplaceHierarchy updateHierarchySiblings(WorkplaceHierarchy wkpHierarchy, String hierarchyCdSelected,
            CreateWorkpceType createType, String newWkpId) {
        String hierarchyCd = wkpHierarchy.getHierarchyCode().v();
        String newHierarchyCd = Strings.EMPTY;

        if (hierarchyCd.compareTo(hierarchyCdSelected) < EQUAL_VALUE) {
            return wkpHierarchy;
        }
        if (hierarchyCd.compareTo(hierarchyCdSelected) == EQUAL_VALUE) {
            // create on top
            if (createType == CreateWorkpceType.CREATE_ON_TOP) {
                newHierarchyCd = hierarchyCdSelected;
            }
            // create on below
            else {
                newHierarchyCd = this.getNewHierarchyCd(hierarchyCdSelected);
            }
        }
        // update hierarchy
        wkpHierarchy.setHierarchyCode(new HierarchyCode(this.getNewHierarchyCd(hierarchyCdSelected)));
        if (hierarchyCd.compareTo(hierarchyCdSelected) > EQUAL_VALUE) {
            return wkpHierarchy;
        }
        // return new wkpHierarchy
        return WorkplaceHierarchy.newInstance(newWkpId, newHierarchyCd);
    }

    /**
     * Gets the new hierarchy cd.
     *
     * @param hierarchyCd
     *            the hierarchy cd
     * @return the new hierarchy cd
     */
    private String getNewHierarchyCd(String hierarchyCd) {
        int length = hierarchyCd.length();
        String charLast = hierarchyCd.substring(length - 1, length);
        return hierarchyCd.replace(charLast, String.valueOf(Integer.parseInt(charLast) + 1));
    }

}
