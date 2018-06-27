/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class NursingLeaveFinder.
 */
@Stateless
public class NursingLeaveFinder {
    
    /** The Constant INDEX_NURSING_SETTING. */
    private static final int INDEX_NURSING_SETTING = 0;
    
    /** The Constant INDEX_CHILD_NURSING_SETTING. */
    private static final int INDEX_CHILD_NURSING_SETTING = 1;
    
    /** The nursing repo. */
    @Inject
    private NursingLeaveSettingRepository nursingRepo;
    
    /** The work type finder. */
    @Inject
    private WorkTypeFinder workTypeFinder;
    
    /**
     * Find nursing leave by company id.
     *
     * @return the list
     */
    public List<NursingLeaveSettingDto> findNursingLeaveByCompanyId() {
        String companyId = AppContexts.user().companyId();
        List<NursingLeaveSetting> listSetting = this.nursingRepo.findByCompanyId(companyId);
        if (CollectionUtil.isEmpty(listSetting)) {
            return null;
        }
        // Find all work type by company id
        //List<WorkTypeDto> listWorkType = this.workTypeFinder.findByCompanyId();
        
        // NURSING
        NursingLeaveSetting nursingSetting = listSetting.get(INDEX_NURSING_SETTING);
        NursingLeaveSettingDto nursingSettingDto = new NursingLeaveSettingDto();
        nursingSetting.saveToMemento(nursingSettingDto);
        
        // CHILD NURSING
        NursingLeaveSetting childNursingSetting = listSetting.get(INDEX_CHILD_NURSING_SETTING);
        NursingLeaveSettingDto childNursingSettingDto = new NursingLeaveSettingDto();
        childNursingSetting.saveToMemento(childNursingSettingDto);
        
        return Arrays.asList(nursingSettingDto, childNursingSettingDto);
    }
    
    /**
     * Find work type.
     *
     * @param listWorkTypeCode the list work type code
     * @return the string
     */
    private String findWorkType(List<WorkTypeDto> listWorkType, List<String> listWorkTypeCode) {
        return listWorkType.stream()
                .filter(item -> listWorkTypeCode.contains(item.getWorkTypeCode()))
                .map(item -> item.getWorkTypeCode() + "." + item.getName())
                .collect(Collectors.joining("、"));
    }
    
    /**
     * Find list work type code by company id.
     *
     * @return the list
     */
    public List<String> findListWorkTypeCodeByCompanyId() {
        String companyId = AppContexts.user().companyId();
        return this.nursingRepo.findWorkTypeCodesByCompanyId(companyId);
    }
}
