/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLItemType;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLSettingItem;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLSettingItemGetMemento;

/**
 * The Class SettingItemDto.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingItemDto {
	/** The linkage code. */
	public String code;
	
	/** The type. */
	public boolean isAggregate;
	
	/** The order number. */
	public int orderNumber;
	
	/**
	 * To domain.
	 *
	 * @return the WL setting item
	 */
	public WLSettingItem toDomain() {
		SettingItemDto dto = this;
		return new WLSettingItem(new WLSettingItemGetMemento() {
			
			@Override
			public WLItemType getType() {
				return dto.isAggregate ? WLItemType.Aggregate : WLItemType.Master;
			}
			
			@Override
			public int getOrderNumber() {
				return dto.orderNumber;
			}
			
			@Override
			public String getLinkageCode() {
				return dto.code;
			}
		});
	}
}
