/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingRepository;
import nts.uk.file.pr.app.export.insurance.data.ChecklistPrintSettingDto;

/**
 * @author duongnd
 *
 */
@Stateless
public class SalarySocialInsuranceQueryProcessor {
    
    /** The checklist print setting repository. */
    //@Inject
    //private ChecklistPrintSettingRepository checklistPrintSettingRepository;

    /**
     * Find configure output setting.
     *
     * @return the checklist print setting dto
     */
    public ChecklistPrintSettingDto findConfigureOutputSetting() {
//      String companyCode = AppContexts.user().companyCode();
//      checklistPrintSettingRepository.findByCompanyCode(companyCode);
      ChecklistPrintSettingDto dto = new ChecklistPrintSettingDto();
      dto.setShowCategoryInsuranceItem(true);
      dto.setShowDetail(true);
      dto.setShowOffice(true);
      dto.setShowTotal(true);
      dto.setShowDeliveryNoticeAmount(true);
      return dto;
    }
}
