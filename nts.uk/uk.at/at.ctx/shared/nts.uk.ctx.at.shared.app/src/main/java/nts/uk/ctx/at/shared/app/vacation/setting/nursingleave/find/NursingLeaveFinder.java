/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.nursingleave.find;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.vacation.setting.nursingleave.find.dto.NursingLeaveSettingDto;
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
}
