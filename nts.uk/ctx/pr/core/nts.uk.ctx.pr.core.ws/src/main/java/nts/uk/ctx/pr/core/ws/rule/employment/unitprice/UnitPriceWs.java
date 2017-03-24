/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.rule.employment.unitprice;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository;
import nts.uk.ctx.pr.core.ws.rule.employment.unitprice.dto.ListUnitPriceCodeModel;
import nts.uk.ctx.pr.core.ws.rule.employment.unitprice.dto.UnitPriceModel;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UnitPriceWs.
 */
@Path("pr/proto/unitprice")
@Produces(MediaType.APPLICATION_JSON)
public class UnitPriceWs {

	/** The unit price repo. */
	@Inject
	private UnitPriceRepository unitPriceRepo;

	/**
	 * Findbydate.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("findbydate/{baseDate}")
	public List<UnitPriceModel> findbydate(@PathParam("baseDate") Integer baseDate) {
		List<UnitPriceModel> listUnitPriceCode = unitPriceRepo
				.getCompanyUnitPrice(baseDate).stream().map(item -> UnitPriceModel.builder()
						.unitPriceCode(item.getCode().v()).unitPriceName(item.getName().v()).build())
				.collect(Collectors.toList());
		return listUnitPriceCode;
	}

	/**
	 * Findbycode.
	 *
	 * @param model the model
	 * @return the list
	 */
	@POST
	@Path("findbycodes")
	public List<UnitPriceModel> findbycode(ListUnitPriceCodeModel model) {
		List<UnitPriceModel> listUnitPriceCode = unitPriceRepo
				.findByCodes(AppContexts.user().companyCode(), model.getCodes()).stream().map(item -> UnitPriceModel
						.builder().unitPriceCode(item.getCode().v()).unitPriceName(item.getName().v()).build())
				.collect(Collectors.toList());
		return listUnitPriceCode;
	}

}
