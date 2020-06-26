package nts.uk.screen.at.ws.kmp.kmp001.b;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kmp.kmp001.b.GetEmployeeInformation;
import nts.uk.screen.at.app.query.kmp.kmp001.b.GetEmployeeInformationDto;
import nts.uk.screen.at.app.query.kmp.kmp001.b.GetEmployeeInformationFromCardNo;
import nts.uk.screen.at.app.query.kmp.kmp001.b.GetEmployeeInformationFromCardNoDto;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/point-card-number-new")
@Produces("application/json")
public class PointCardNumberNewWs extends WebService {
	
	@Inject 
	private GetEmployeeInformation getEmployeeInformation;
	
	@Inject
	private GetEmployeeInformationFromCardNo  getEmployeeInformationFromCardNo;
	
	@POST
	@Path("getEmployeeInformation/{sid}")
	public GetEmployeeInformationDto getEmployeeInformation(@PathParam("sid") String sid) {
		return this.getEmployeeInformation.finDto(sid);
	}
	
	@POST
	@Path("get-employee-information-from-card-no/{cardNumber}")
	public GetEmployeeInformationFromCardNoDto getEmployeeInformationFromCardNo (@PathParam("cardNumber") String cardnumber){
		return this.getEmployeeInformationFromCardNo.find(cardnumber);
	}
}
