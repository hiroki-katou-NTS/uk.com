/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.insurance.find;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.app.insurance.find.dto.CheckListPrintSettingFindOutDto;

@Stateless
public class ChecklistPrintSettingFinder {

	/** The checklist print setting repository. */
	/*@Inject
	private ChecklistPrintSettingRepository checklistPrintSettingRepository;
	*/

	/**
	 * Find.
	 *
	 * @return the check list print setting find out dto
	 */
	public CheckListPrintSettingFindOutDto find() {
//	    String companyCode = AppContexts.user().companyCode();
//		CheckListPrintSettingFindOutDto checkListPrintSettingFindOutDto = new CheckListPrintSettingFindOutDto();
//		Optional<ChecklistPrintSetting> optionalChecklistPrintSetting = checklistPrintSettingRepository
//				.findByCompanyCode(companyCode);
//		if (optionalChecklistPrintSetting.isPresent()) {
//			optionalChecklistPrintSetting.get().saveToMemento(checkListPrintSettingFindOutDto);
//			return checkListPrintSettingFindOutDto;
//		}
//		return null;
		CheckListPrintSettingFindOutDto dto = new CheckListPrintSettingFindOutDto();
        dto.setShowCategoryInsuranceItem(true);
        dto.setShowDetail(true);
        dto.setShowOffice(false);
        dto.setShowTotal(false);
        dto.setShowDeliveryNoticeAmount(false);
        return dto;
	}

}
