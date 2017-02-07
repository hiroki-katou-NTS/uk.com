/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.insurance;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import dto.CheckListPrintSettingDto;

@Path("ctx/pr/report/insurance/")
@Produces("application/json")
public class CheckListPrintSettingWebservice {
	
	/**
	 * Find all.
	 *
	 * @return the check list print setting dto
	 */
	@POST
	@Path("findAll")
	public CheckListPrintSettingDto findAll(){		
		CheckListPrintSettingDto dto=new CheckListPrintSettingDto();
			dto.setShowCategoryInsuranceItem(false);
			dto.setShowDeliveryNoticeAmount(true);
			dto.setShowDetail(true);
			dto.setShowOffice(false);
		return dto;
	}
}
