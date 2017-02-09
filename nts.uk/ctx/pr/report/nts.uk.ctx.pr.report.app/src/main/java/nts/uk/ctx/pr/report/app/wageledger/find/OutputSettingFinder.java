/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.app.wageledger.find.dto.OutputSettingDto;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputSettingFinder.
 */
@Stateless
public class OutputSettingFinder {
	
	/** The repository. */
	@Inject
	private WLOutputSettingRepository repository;
	
	/**
	 * Find.
	 *
	 * @param code the code
	 * @return the output setting dto
	 */
	public OutputSettingDto find(String code) {
		WLOutputSetting outputSetting = this.repository.find(null, AppContexts.user().companyCode());
		OutputSettingDto dto = OutputSettingDto.builder().build();
		outputSetting.saveToMemento(dto);
		// TODO: Find name item in Setting items.
		return dto;
	}
}
