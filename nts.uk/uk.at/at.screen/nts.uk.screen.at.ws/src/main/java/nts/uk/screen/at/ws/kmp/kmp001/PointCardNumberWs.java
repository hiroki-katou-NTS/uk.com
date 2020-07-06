package nts.uk.screen.at.ws.kmp.kmp001;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.query.kmp.kmp001.CardUnregistered;
import nts.uk.screen.at.app.query.kmp.kmp001.CardUnregisteredDto;
import nts.uk.screen.at.app.query.kmp.kmp001.EmployeeInformationFromCardNo;
import nts.uk.screen.at.app.query.kmp.kmp001.EmployeeInformationFromCardNoDto;
import nts.uk.screen.at.app.query.kmp.kmp001.InformationEmployee;
import nts.uk.screen.at.app.query.kmp.kmp001.InformationEmployeeDto;
import nts.uk.screen.at.app.query.kmp.kmp001.StampCardDigit;
/**
 * 
 * @author chungnt
 *
 */
import nts.uk.screen.at.app.query.kmp.kmp001.StampCardDigitDto;

@Path("screen/pointCardNumber")
@Produces("application/json")
public class PointCardNumberWs extends WebService {
	
	
	@Inject 
	private CardUnregistered cardUnregistered;
	
	@Inject 
	private InformationEmployee informationEmployee;
	
	@Inject
	private EmployeeInformationFromCardNo  getEmployeeInformationFromCardNo;
	
	@Inject
	private StampCardDigit stampCardDigit;
	
	@POST
	@Path("getAllCardUnregister/{start}/{end}")
	public List<CardUnregisteredDto> get(@PathParam("start") String start,@PathParam("end") String end) {
		return this.cardUnregistered.getAll(new DatePeriod(GeneralDate.fromString(start, "yyyy-MM-dd"),GeneralDate.fromString(end, "yyyy-MM-dd")));
	}
	
	@POST
	@Path("getEmployeeInformation/{sid}")
	public InformationEmployeeDto get(@PathParam("sid") String sid) {
		return this.informationEmployee.get(sid);
	}
	
	@POST
	@Path("getEmployeeFromCardNo/{cardNumber}")
	public List<EmployeeInformationFromCardNoDto> getEmployeeInformationFromCardNo (@PathParam("cardNumber") String cardnumber) {
		return this.getEmployeeInformationFromCardNo.getEmployee(cardnumber);
	}
	
	@POST
	@Path("getAllEmployeeFromCardNo")
	public List<EmployeeInformationFromCardNoDto> getAllEmployeeInformationFromCardNo() {
		return this.getEmployeeInformationFromCardNo.getAll();
	}
	
	@POST
	@Path("getStampCardDigit")
	public StampCardDigitDto getStampCardDigitNumber() {
		return this.stampCardDigit.get();
	}
}
