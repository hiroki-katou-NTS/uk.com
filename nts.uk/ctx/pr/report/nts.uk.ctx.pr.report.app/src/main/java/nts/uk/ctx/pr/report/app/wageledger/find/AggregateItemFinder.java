/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.app.wageledger.find.dto.AggregateItemDto;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.SubItemDto;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;

/**
 * The Class AggregateItemFinder.
 */
@Stateless
public class AggregateItemFinder {
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AggregateItemDto> findAll() {
		// Fake data.
		List<AggregateItemDto> dtos = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			dtos.add(AggregateItemDto.builder().code("AG" + i).name("Aggregate item " + i)
					.showNameZeroValue(i % 2 != 0).showValueZeroValue(i % 2 == 0).paymentType(PaymentType.Salary)
					.category(WLCategory.Payment).build());
			dtos.add(AggregateItemDto.builder().code("AG" + i).name("Aggregate item " + i)
					.showNameZeroValue(i % 2 != 0).showValueZeroValue(i % 2 == 0).paymentType(PaymentType.Salary)
					.category(WLCategory.Deduction).build());
		}
		return dtos;
	}
	
	/**
	 * Find by category and payment type.
	 *
	 * @param category the category
	 * @param paymentType the payment type
	 * @return the list
	 */
	public List<AggregateItemDto> findByCategoryAndPaymentType(WLCategory category, PaymentType paymentType) {
		// Fake data.
		List<AggregateItemDto> dtos = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			dtos.add(AggregateItemDto.builder().code("AG" + i).name("Aggregate item " + i)
					.showNameZeroValue(i % 2 != 0).showValueZeroValue(i % 2 == 0).paymentType(paymentType)
					.category(category).build());
		}
		return dtos;
	}
	
	/**
	 * Find detail.
	 *
	 * @param code the code
	 * @return the aggregate item dto
	 */
	public AggregateItemDto findDetail(String code) {
		// Fake data.
		List<SubItemDto> subItemDtos = new ArrayList<>();
		for (int i = 0 ; i < 10 ; i++) {
			subItemDtos.add(SubItemDto.builder()
					.code("S" + i)
					.name("Aggregate sub item " + i)
					.build());
		}
		return AggregateItemDto.builder()
				.category(WLCategory.Payment)
				.paymentType(PaymentType.Salary)
				.code(code)
				.name("Aggregate item " + code)
				.showNameZeroValue(false)
				.showValueZeroValue(false)
				.subItems(subItemDtos)
				.build();
	}
}
