/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.config;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WorkplaceConfigDto;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceBase;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceStateDto;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceConfigFinder.
 */
@Stateless
public class WorkplaceConfigFinder {

    /** The wkp config repo. */
    @Inject
    private WorkplaceConfigRepository wkpConfigRepo;

    /** The wkp config info repo. */
    @Inject
    private WorkplaceConfigInfoRepository wkpConfigInfoRepo;

    /** The Constant MAX_WORKPLACE. */
    private static final Integer MAX_WORKPLACE = 9999;

    /** The Constant MAX_SIBLINGS. */
    private static final Integer MAX_SIBLINGS = 999;

    /** The Constant HIERARCHY_LENGTH. */
    private static final Integer HIERARCHY_LENGTH = 3;

    /** The Constant MAX_HIERARCHY. */
    private static final Integer MAX_HIERARCHY = 10;
    
    /** The Constant FIRST. */
    private static final Integer FIRST = 0;

    /**
     * Find all by company id.
     *
     * @return the workplace config dto
     */
    public WorkplaceConfigDto findAllByCompanyId() {
        String companyId = AppContexts.user().companyId();

        Optional<WorkplaceConfig> optional = this.wkpConfigRepo.findAllByCompanyId(companyId);
        if (!optional.isPresent()) {
            return null;
        }
        WorkplaceConfigDto wkpConfigDto = new WorkplaceConfigDto();
        optional.get().saveToMemento(wkpConfigDto);
        return wkpConfigDto;
    }

    /**
     * Valid workplace.
     *
     * @param workplaceDto
     *            the workplace dto
     */
    public WorkplaceStateDto checkWorkplace(WorkplaceBase workplaceDto) {
        String companyId = AppContexts.user().companyId();

        Optional<WorkplaceConfig> optionalWkpConfig = this.wkpConfigRepo.findByStartDate(companyId,
                workplaceDto.getStartDate());
        if (!optionalWkpConfig.isPresent()) {
            throw new RuntimeException(String.format("Not have %s", workplaceDto.getStartDate()));
        }
        String historyId = optionalWkpConfig.get().getWkpConfigHistoryLatest().identifier();

        return this.validWokplaceHierarchy(companyId, historyId, workplaceDto.getWorkplaceId());
    }

    /**
     * Valid wokplace hierarchy.
     *
     * @param companyId
     *            the company id
     * @param historyId
     *            the history id
     * @param selectedWkpId
     *            the selected wkp id
     */
    private WorkplaceStateDto validWokplaceHierarchy(String companyId, String historyId, String selectedWkpId) {
        WorkplaceStateDto wkpStateDto = new WorkplaceStateDto(true, true);
        Optional<WorkplaceConfigInfo> optionalWkpConfigInfo = this.wkpConfigInfoRepo.find(companyId, historyId);
        if (!optionalWkpConfigInfo.isPresent()) {
            return wkpStateDto;
        }
        WorkplaceConfigInfo wkpConfigInfo = optionalWkpConfigInfo.get();
        if (wkpConfigInfo.getLstWkpHierarchy().size() >= MAX_WORKPLACE) {
            throw new BusinessException("Msg_367");
        }

        Optional<WorkplaceConfigInfo> optionalWkpConfigInfoSelected = this.wkpConfigInfoRepo.find(companyId, historyId,
                selectedWkpId);
        if (!optionalWkpConfigInfoSelected.isPresent()) {
            throw new RuntimeException(String.format("Not have workplace %s", selectedWkpId));
        }
        String hierarchyCdSeletected = optionalWkpConfigInfoSelected.get().getLstWkpHierarchy().get(FIRST)
                .getHierarchyCode().v();

        String hierarchyCdParent = hierarchyCdSeletected.substring(FIRST,
                hierarchyCdSeletected.length() - HIERARCHY_LENGTH);

        boolean isLessMaxSiblings = wkpConfigInfo.getLstWkpHierarchy().stream()
                .filter(item -> {
                    String hierarchyCd = item.getHierarchyCode().v();
                    return hierarchyCd.length() == hierarchyCdSeletected.length()
                            && hierarchyCd.startsWith(hierarchyCdParent);
                }).count() < MAX_SIBLINGS;

        boolean isLessMaxHierarchy = (hierarchyCdSeletected.length() / HIERARCHY_LENGTH) < MAX_HIERARCHY;
        
        // if has error, throw message.
        if (!isLessMaxHierarchy && !isLessMaxSiblings) {
            BundledBusinessException exceptions = BundledBusinessException.newInstance();
            exceptions.addMessages("Msg_368", "Msg_369");
            exceptions.throwExceptions();
        }
        // set infor return
        wkpStateDto.setIsLessMaxHierarchy(isLessMaxHierarchy);
        wkpStateDto.setIsLessMaxSiblings(isLessMaxSiblings);
        
        return wkpStateDto;
    }
}
