/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.ws.qpp;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dto.InsuranceOfficeDto;
import nts.arc.layer.ws.WebService;

/**
 * The Class QPP018WebService.
 */
@Path("/screen/pr/QPP018")
@Produces("application/json")
public class QPP018WebService extends WebService {
	
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@POST
	@Path("getAll")
	public List<InsuranceOfficeDto> getAll(){
		// Fake data.
		List<InsuranceOfficeDto> officeList=new ArrayList<>();
		InsuranceOfficeDto insOffice1=new InsuranceOfficeDto("001", "Office 01");
		InsuranceOfficeDto insOffice2=new InsuranceOfficeDto("002", "Office 02");
		officeList.add(insOffice1);
		officeList.add(insOffice2);
		
		return officeList;
	} 
}

