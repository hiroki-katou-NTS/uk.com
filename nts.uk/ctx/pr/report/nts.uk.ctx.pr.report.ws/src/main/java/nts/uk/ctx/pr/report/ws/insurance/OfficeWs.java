/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.insurance;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.insurance.find.dto.InsuranceOfficeFindOutDto;

/**
 * The Class OfficeWs.
 */
@Path("ctx/pr/report/insurance/office")
@Produces("application/json")
public class OfficeWs extends WebService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<InsuranceOfficeFindOutDto> findAll() {
		// Fake data.
		List<InsuranceOfficeFindOutDto> officeList = new ArrayList<>();
		InsuranceOfficeFindOutDto insOffice1 = new InsuranceOfficeFindOutDto();
		insOffice1.setCode("001");
		insOffice1.setName("Office 01");
		InsuranceOfficeFindOutDto insOffice2 = new InsuranceOfficeFindOutDto();
		insOffice2.setCode("002");
		insOffice2.setName("Office 02");
		officeList.add(insOffice1);
		officeList.add(insOffice2);

		return officeList;
	}

}
