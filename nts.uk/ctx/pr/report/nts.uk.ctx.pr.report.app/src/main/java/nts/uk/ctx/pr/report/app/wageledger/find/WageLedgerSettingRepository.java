/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find;

import java.util.List;

import nts.uk.ctx.pr.report.app.wageledger.find.dto.HeaderSettingDto;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;

/**
 * The Interface WageLedgerSettingRepository.
 */
public interface WageLedgerSettingRepository {
	
	/**
	 * Find header output settings.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<HeaderSettingDto> findHeaderOutputSettings(CompanyCode companyCode);
	
	/**
	 * Find header aggregate items by category.
	 *
	 * @param companyCode the company code
	 * @param category the category
	 * @param paymentType the payment type
	 * @return the list
	 */
	List<HeaderSettingDto> findHeaderAggregateItemsByCategory(CompanyCode companyCode,
			WLCategory category, PaymentType paymentType);
	
	/**
	 * Find header aggregate items.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	default List<HeaderSettingDto> findHeaderAggregateItems(CompanyCode companyCode) {
		return this.findHeaderAggregateItemsByCategory(companyCode, null, null);
	}
}
