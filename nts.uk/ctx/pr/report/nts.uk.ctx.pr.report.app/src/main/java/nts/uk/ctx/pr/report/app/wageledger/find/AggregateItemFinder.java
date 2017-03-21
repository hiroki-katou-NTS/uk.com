/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.pr.report.app.wageledger.command.dto.ItemSubjectDto;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.AggregateItemDto;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.HeaderSettingDto;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AggregateItemFinder.
 */
@Stateless
public class AggregateItemFinder {
	
	/** The repository. */
	@Inject
	private WLAggregateItemRepository repository;
	
	@Inject
	private WageLedgerSettingRepository wageLedgerSettingRepository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<HeaderSettingDto> findAll() {
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
		return this.wageLedgerSettingRepository.findHeaderAggregateItems(companyCode);
	}
	
	/**
	 * Find by category and payment type.
	 *
	 * @param category the category
	 * @param paymentType the payment type
	 * @return the list
	 */
	public List<HeaderSettingDto> findByCategoryAndPaymentType(WLCategory category, PaymentType paymentType) {
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
		return this.wageLedgerSettingRepository
				.findHeaderAggregateItemsByCategory(companyCode, category, paymentType);
	}
	
	/**
	 * Find detail.
	 *
	 * @param code the code
	 * @return the aggregate item dto
	 */
	public AggregateItemDto findDetail(ItemSubjectDto subject) {
		String companyCode = AppContexts.user().companyCode();
		
		// Query data.
		val aggregateItem = this.repository.findByCode(subject.toDomain(companyCode));
		
		// Return dto.
		val dto = AggregateItemDto.builder().build();
		aggregateItem.saveToMemento(dto);
		// TODO: Get master item name.
		// Fake master item name.
		dto.subItems.forEach(item -> {
			item.name = "Master Item " + item.code;
		});
		return dto;
	}
}
