/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.ItemSelectionType;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemDailyWorkScheduleDeleteHandler.
 * @author HoangDD
 */
@Stateless
public class OutputItemDailyWorkScheduleDeleteHandler {
	
	/** The repository. */
	@Inject 
	OutputStandardSettingRepository standardSettingRepository;
	
	@Inject
	FreeSettingOfOutputItemRepository freeSettingOfOutputItemRepository;
	
	/**
	 * Delete.
	 *
	 * @param code the code
	 */
	public void delete(String layoutId, Integer selectionType) {
		String companyId = AppContexts.user().companyId();

		if (selectionType == ItemSelectionType.STANDARD_SELECTION.value) {
			this.standardSettingRepository.deleteStandardSetting(companyId, layoutId);
		}
		
		if (selectionType == ItemSelectionType.FREE_SETTING.value) {
			String employeeId = AppContexts.user().employeeId();
			this.freeSettingOfOutputItemRepository.deleteFreeSetting(companyId, employeeId, layoutId);
		}
	}
}
