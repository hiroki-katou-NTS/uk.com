/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.insurance.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.pr.report.app.insurance.find.dto.CheckListPrintSettingFindOutDto;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSetting;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class ChecklistPrintSettingFinder {

	/** The checklist print setting repository. */
	@Inject
	private ChecklistPrintSettingRepository checklistPrintSettingRepository;

	public CheckListPrintSettingFindOutDto find() {
		CheckListPrintSettingFindOutDto checkListPrintSettingFindOutDto = new CheckListPrintSettingFindOutDto();
		Optional<ChecklistPrintSetting> optionalChecklistPrintSetting = checklistPrintSettingRepository
				.findByCompanyCode(new CompanyCode(AppContexts.user().companyCode()));
		if (optionalChecklistPrintSetting.isPresent()) {
			optionalChecklistPrintSetting.get().saveToMemento(checkListPrintSettingFindOutDto);
			return checkListPrintSettingFindOutDto;
		}
		return null;
	}

}
