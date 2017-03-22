/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.app.wageledger.find.dto.HeaderSettingDto;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.OutputSettingDto;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode;
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
		WLOutputSetting outputSetting = this.repository.findByCode(
				new CompanyCode(AppContexts.user().companyCode()),
				new WLOutputSettingCode(code));
		
		OutputSettingDto dto = OutputSettingDto.builder().build();
		outputSetting.saveToMemento(dto);
		// TODO: Find item name in Setting items.
		// Fake master item name.
		if (dto.categorySettings != null) {
			dto.categorySettings.forEach(cate -> {
				cate.outputItems.forEach(item -> {
					if (!item.isAggregateItem) {
						item.name = "Master item " + item.code;
					}
				});
			});
		}
		return dto;
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<HeaderSettingDto> findAll(){
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
		return this.repository.findAll(companyCode).stream().map(setting -> {
			return HeaderSettingDto.builder()
					.code(setting.getCode().v())
					.name(setting.getName().v())
					.build();
		}).collect(Collectors.toList());
	}
}
