package employment.procesing.yearmonth;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import employment.processing.yearmonth.PaydayProcessing;
import employment.processing.yearmonth.PaydayProcessingDto;
@Path("pr/core")
@Produces("application/json")
public class PaydayProcessingWebservice {
	
	@Inject
	private PaydayProcessing processing;
	@POST
	@Path("paydayrocessing/getbyccd")
	public List<PaydayProcessingDto> getPaydayProcessing(){
		return this.processing.getPaydayProcessing("");
	}
}
