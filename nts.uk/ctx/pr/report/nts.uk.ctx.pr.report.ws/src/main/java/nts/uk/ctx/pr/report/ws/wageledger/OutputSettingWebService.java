/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.wageledger;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WageLedgerCategory;
import nts.uk.ctx.pr.report.ws.wageledger.dto.CategorySettingDto;
import nts.uk.ctx.pr.report.ws.wageledger.dto.OutputSettingDto;
import nts.uk.ctx.pr.report.ws.wageledger.dto.SettingItemDto;

/**
 * The Class OutputSettingWebService.
 */
@Path("ctx/pr/report/wageledger/outputsetting")
@Produces("application/json")
public class OutputSettingWebService extends WebService{
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<OutputSettingDto> findAll(){
		// Mock data.
		List<OutputSettingDto> dtos = new ArrayList<>();
		for (int i = 0; i <= 10; i++) {
			dtos.add(OutputSettingDto.builder()
					.code("00" + i)
					.name("Output setting " + i)
					.isOnceSheetPerPerson(true)
					.build());
		}
		return dtos;
	}
	
	/**
	 * Find detail.
	 *
	 * @param code the code
	 * @return the output setting dto
	 */
	@POST
	@Path("find/{code}")
	public OutputSettingDto findDetail(@PathParam("code") String code) {
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
				.category(WageLedgerCategory.Payment)
				.paymentType(PaymentType.Salary)
				.outputItems(settingItemDtos)
				.build());
		categories.add(CategorySettingDto.builder()
				.category(WageLedgerCategory.Deduction)
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
}
