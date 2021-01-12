/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface NursingVacationSettingRepository.
 */
public interface NursingLeaveSettingRepository {
    
    /**
     * Adds the.
     *
     * @param nursingSetting the nursing setting
     * @param childNursingSetting the child nursing setting
     */
    void add(NursingLeaveSetting nursingSetting, NursingLeaveSetting childNursingSetting);
    
    /**
     * Update.
     *
     * @param nursingSetting the nursing setting
     * @param childNursingSetting the child nursing setting
     */
    void update(NursingLeaveSetting nursingSetting, NursingLeaveSetting childNursingSetting);
    
    /**
     * Find by company id.
     *
     * @param companyId the company id
     * @return the list
     */
    List<NursingLeaveSetting> findByCompanyId(String companyId);
    
    /**
     * 
     * @param companyId
     * @param nursingCategory
     * @return
     */
    NursingLeaveSetting findByCompanyIdAndNursingCategory(String companyId, Integer nursingCategory );
    
    /**
     * Find work type codes by company id.
     *
     * @param companyId the company id
     * @return the list
     */
    List<String> findWorkTypeCodesByCompanyId(String companyId);
    
    /**
     * ドメインモデル「介護看護休暇設定」の「管理区分」を取得する。
     * 
     * @param companyId 会社ID
     * @param nursingCategory 介護看護区分
     * @return
     */
    NursingLeaveSetting findManageDistinctByCompanyIdAndNusingCategory(String companyId, Integer nursingCategory);
}
