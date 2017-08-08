package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttendanceItemsFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;

@Path("at/record/businesstype")
@Produces("application/json")
public class AttendanceItemWebService extends WebService {
	
	@Inject
	private AttendanceItemsFinder attendanceItemsFinder;
	
	@POST
	@Path("attendanceItem/findAll")
	public List<AttendanceItemDto> getAll(){
		return this.attendanceItemsFinder.find();
	}

}
