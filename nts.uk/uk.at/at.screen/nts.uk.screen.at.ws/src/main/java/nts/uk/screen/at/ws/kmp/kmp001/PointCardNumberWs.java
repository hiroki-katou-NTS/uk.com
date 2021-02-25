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
import nts.uk.screen.at.app.query.kmp.kmp001.a.EmployeeInfoCardNumber;
import nts.uk.screen.at.app.query.kmp.kmp001.a.EmployeeInfoCardNumberDto;
import nts.uk.screen.at.app.query.kmp.kmp001.a.ExtractedEmployeeCardSetting;
import nts.uk.screen.at.app.query.kmp.kmp001.a.ExtractedEmployeeCardSettingDto;
import nts.uk.screen.at.app.query.kmp.kmp001.a.StampCardDigit;
import nts.uk.screen.at.app.query.kmp.kmp001.a.StampCardDigitDto;
import nts.uk.screen.at.app.query.kmp.kmp001.b.EmployeeInfoFromCardNo;
import nts.uk.screen.at.app.query.kmp.kmp001.b.EmployeeInfoFromCardNoDto;
import nts.uk.screen.at.app.query.kmp.kmp001.b.InformationEmployeeDtoViewB;
import nts.uk.screen.at.app.query.kmp.kmp001.b.InformationEmployeeViewB;
import nts.uk.screen.at.app.query.kmp.kmp001.b.StampCardEmployee;
import nts.uk.screen.at.app.query.kmp.kmp001.b.StampCardEmployeeDto;
import nts.uk.screen.at.app.query.kmp.kmp001.c.CardUnregistered;
import nts.uk.screen.at.app.query.kmp.kmp001.c.CardUnregisteredDto;
import nts.uk.screen.at.app.query.kmp.kmp001.c.InformationEmployeeDtoViewC;
import nts.uk.screen.at.app.query.kmp.kmp001.c.InformationEmployeeViewC;
import nts.uk.screen.at.app.query.kmp.kmp001.e.CardNumbersMassGenerated;
import nts.uk.screen.at.app.query.kmp.kmp001.e.CardNumbersMassGeneratedDto;
import nts.uk.screen.at.app.query.kmp.kmp001.e.CardNumbersMassGeneratedInput;

@Path("screen/pointCardNumber")
@Produces("application/json")
public class PointCardNumberWs extends WebService {
	
	@Inject 
	private CardUnregistered cardUnregistered;
	
	@Inject 
	private InformationEmployeeViewB informationEmployeeViewB;
	
	@Inject 
	private InformationEmployeeViewC informationEmployeeViewC;
	
	@Inject
	private EmployeeInfoFromCardNo  getEmployeeInformationFromCardNo;
	
	@Inject
	private StampCardDigit stampCardDigit;
	
	@Inject
	private ExtractedEmployeeCardSetting extractedEmployeeCardSetting;
	
	@Inject
	private EmployeeInfoCardNumber employeeInfoCardNumber;
	
	@Inject
	private StampCardEmployee stampCardViewB;
	
	@Inject
	private CardNumbersMassGenerated cardNumbersMassGenerated;
	
	@POST
	@Path("getAllCardUnregister/{start}/{end}")
	public List<CardUnregisteredDto> get(@PathParam("start") String start,@PathParam("end") String end) {
		return this.cardUnregistered.getAll(new DatePeriod(GeneralDate.fromString(start, "yyyy-MM-dd"),GeneralDate.fromString(end, "yyyy-MM-dd")));
	}
	
	@POST
	@Path("getEmployeeInformationViewB/{sid}")
	public InformationEmployeeDtoViewB getInfoEmployeeViewB(@PathParam("sid") String sid) {
		return this.informationEmployeeViewB.get(sid);
	}
	
	@POST
	@Path("getEmployeeInformationViewC/{sid}")
	public InformationEmployeeDtoViewC getInfoEmployeeViewC(@PathParam("sid") String sid) {
		return this.informationEmployeeViewC.get(sid);
	}
	
	@POST
	@Path("getEmployeeFromCardNo/{cardNumber}")
	public List<StampCardEmployeeDto> getEmployeeInformationFromCardNo (@PathParam("cardNumber") String cardnumber) {
		return this.stampCardViewB.getStampCard(cardnumber);
	}
	
	@POST
	@Path("getAllEmployeeFromCardNo")
	public EmployeeInfoFromCardNoDto getAllEmployeeInformationFromCardNo() {
		return this.getEmployeeInformationFromCardNo.getAll();
	}
	
	@POST
	@Path("getStampCardDigit")
	public StampCardDigitDto getStampCardDigitNumber() {
		return this.stampCardDigit.get();
	}
	
	@POST
	@Path("getStatusEmployeeSettingStampCard")
	public List<ExtractedEmployeeCardSettingDto> getStatusEmployee (List<String> employees) {
		return this.extractedEmployeeCardSetting.getStatusStampCard(employees);
	}
	
	@POST
	@Path("getEmployeeInfoCardNumber/{employeeId}/{workplaceId}/{baseDate}")
	public EmployeeInfoCardNumberDto getInfoEmployeeCardNumber (@PathParam("employeeId") String employeeId,
																@PathParam("workplaceId") String workplaceId,
																@PathParam("baseDate") String baseDate) {
		return this.employeeInfoCardNumber.getInfoEmployeeandStamCard(employeeId, workplaceId, GeneralDate.fromString(baseDate, "yyyy-MM-dd"));
	}
	
	@POST
	@Path("getStampCardGenerated")
	public List<CardNumbersMassGeneratedDto> getStampCardGenerated(CardNumbersMassGeneratedInput input) {
		return this.cardNumbersMassGenerated.get(input);
	}
}
