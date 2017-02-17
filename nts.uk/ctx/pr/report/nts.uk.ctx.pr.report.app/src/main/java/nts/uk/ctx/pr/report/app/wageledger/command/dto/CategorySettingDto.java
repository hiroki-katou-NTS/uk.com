/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Setter;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingGetMemento;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLSettingItem;

@Setter
public class CategorySettingDto {
	/** The category. */
	private WLCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/** The output items. */
	private List<SettingItemDto> outputItems;
	
	/**
	 * To domain.
	 *
	 * @return the WL category setting
	 */
	public WLCategorySetting toDomain() {
		CategorySettingDto dto = this;
		return new WLCategorySetting(new WLCategorySettingGetMemento() {
			
			@Override
			public PaymentType getPaymentType() {
				return dto.paymentType;
			}
			
			@Override
			public List<WLSettingItem> getOutputItems() {
				return dto.outputItems.stream().map((item) -> {
					return item.toDomain();
				}).collect(Collectors.toList());
			}
			
			@Override
			public WLCategory getCategory() {
				return dto.category;
			}
		});
	}
}
