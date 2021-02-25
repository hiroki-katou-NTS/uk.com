package nts.uk.screen.at.ws.ksu.ksus01.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.ksus01.a.GetInforInitialStartup;
import nts.uk.screen.at.app.ksus01.a.GetInforOnTargetDate;
import nts.uk.screen.at.app.ksus01.a.GetInforOnTargetPeriod;
import nts.uk.screen.at.app.ksus01.a.InforOnTargetDateInput;
import nts.uk.screen.at.app.ksus01.a.InforOnTargetPeriodDto;
import nts.uk.screen.at.app.ksus01.a.InforOnTargetPeriodInput;
import nts.uk.screen.at.app.ksus01.a.InforOnTargetDateDto;
import nts.uk.screen.at.app.ksus01.a.InitInformationDto;

/**
 * 
 * @author dungbn
 *
 */

@Path("screen/at/ksus01/a")
@Produces(MediaType.APPLICATION_JSON)
public class Ksus01AWebServive {
	
	@Inject
	private GetInforInitialStartup getInforInitialStartup;
	
	@Inject
	private GetInforOnTargetPeriod getInforOnTargetPeriod;
	
	@Inject
	private GetInforOnTargetDate getInforOnTargetDate;
	
	@POST
	@Path("getinforinitial")
	public InitInformationDto getInitInformation() {
		return this.getInforInitialStartup.handle();
	}
	
	@POST
	@Path("getinfortargetperiod")
	public InforOnTargetPeriodDto getInfoTargetPeriod(InforOnTargetPeriodInput input) {
		return this.getInforOnTargetPeriod.handle(input);
	}
	
	@POST
	@Path("getinfortargetdate")
	public InforOnTargetDateDto getInfoTargetDate(InforOnTargetDateInput input) {
		return this.getInforOnTargetDate.handle(input.getDesiredSubmissionStatus(), input.getWorkHolidayAtr(), input.getTargetDate());
	}
	
}
