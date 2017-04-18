/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.app.itemmaster.find.ItemMaterFinder;
import nts.uk.ctx.pr.report.app.itemmaster.find.MasterItemDto;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.HeaderSettingDto;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.OutputSettingDto;
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
	
	@Inject
	private ItemMaterFinder finder;
	
	/**
	 * Find.
	 *
	 * @param code the code
	 * @return the output setting dto
	 */
	public OutputSettingDto find(String code) {
		String companyCode = AppContexts.user().companyCode();
		WLOutputSetting outputSetting = this.repository.findByCode(companyCode,
				new WLOutputSettingCode(code));
		
		OutputSettingDto dto = OutputSettingDto.builder().build();
		outputSetting.saveToMemento(dto);
		
		// Add master item name to output setting.
		List<String> masterItemCode = dto.categorySettings.stream()
				.map(category -> category.outputItems.stream()
						.map(item -> item.code)
						.collect(Collectors.toList()))
				.flatMap(item -> item.stream())
				.collect(Collectors.toList());
		Map<String, MasterItemDto> masterItemMap = this.finder.findByCodes(companyCode, masterItemCode)
				.stream().collect(Collectors.toMap(item -> item.code, Function.identity()));
		dto.categorySettings.forEach(cate -> {
			cate.outputItems.forEach(item -> {
				if (!item.isAggregateItem) {
					item.name = masterItemMap.get(item.code).name;
				}
			});
		});
		return dto;
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<HeaderSettingDto> findAll(){
		String companyCode = AppContexts.user().companyCode(); 
		return this.repository.findAll(companyCode).stream().map(setting -> {
			return HeaderSettingDto.builder()
					.code(setting.getCode().v())
					.name(setting.getName().v())
					.build();
		}).collect(Collectors.toList());
	}
}
