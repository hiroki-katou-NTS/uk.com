/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.app.wageledger.find.dto.CategorySettingDto;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.OutputSettingDto;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.SettingItemDto;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
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
		WLOutputSetting outputSetting = this.repository.findByCode(null, AppContexts.user().companyCode());
		
		// Fake data.
		if (outputSetting == null) {
			int codeInt = Integer.parseInt(code.substring(code.length() - 1, code.length()));
			List<SettingItemDto> settingItemDtos = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				settingItemDtos.add(SettingItemDto.builder()
						.code("ITEM" + i)
						.name("item " + i)
						.orderNumber(i)
						.isAggregateItem(i % 2 == 0).build());
			}
			List<CategorySettingDto> categories = new ArrayList<>();
			categories.add(CategorySettingDto.builder()
					.category(WLCategory.Payment)
					.paymentType(PaymentType.Salary)
					.outputItems(settingItemDtos)
					.build());
			categories.add(CategorySettingDto.builder()
					.category(WLCategory.Deduction)
					.paymentType(PaymentType.Salary)
					.outputItems(settingItemDtos)
					.build());
			return OutputSettingDto.builder()
					.code(code)
					.name("Output setting " + codeInt)
					.isOnceSheetPerPerson(false)
					.categorySettings(categories)
					.build();
		}
		
		OutputSettingDto dto = OutputSettingDto.builder().build();
		outputSetting.saveToMemento(dto);
		// TODO: Find item name in Setting items.
		return dto;
	}
}
