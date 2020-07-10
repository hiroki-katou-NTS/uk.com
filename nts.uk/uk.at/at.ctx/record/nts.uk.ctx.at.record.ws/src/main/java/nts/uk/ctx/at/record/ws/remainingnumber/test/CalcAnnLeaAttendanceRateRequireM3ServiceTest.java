package nts.uk.ctx.at.record.ws.remainingnumber.test;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test.CalcAnnLeaAttendanceRateRequireM3;

@Path("create/binary")
@Produces("application/json")
public class CalcAnnLeaAttendanceRateRequireM3ServiceTest extends WebService {
	
	private CalcAnnLeaAttendanceRateRequireM3 calcAnnLeaAttendanceRateRequireM3;
	
	@POST
	@Path("calcrem")
	public void startup() {
		calcAnnLeaAttendanceRateRequireM3 = new CalcAnnLeaAttendanceRateRequireM3();
		calcAnnLeaAttendanceRateRequireM3.testService();
	}
	
}