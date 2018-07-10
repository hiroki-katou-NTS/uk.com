package nts.uk.ctx.at.function.ws.attendancetype;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.attendancetype.AttendanceTypeDto;
import nts.uk.ctx.at.function.app.find.attendancetype.AttendanceTypeFinder;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Path("at/share/attendanceType")
@Produces("application/json")
public class AttendanceTypeWebService extends WebService{
	
	@Inject
	private AttendanceTypeFinder attendanceTypeFinder;
	
	@POST
	@Path("getByType")
	public List<AttendanceTypeDto> getItemByScreenUseAtr(){
		return this.attendanceTypeFinder.getItemByScreenUseAtr(0);
	} 
	
	@POST
	@Path("getAtdId")
	public List<AttendanceTypeDto> getListAtd(){
		// 時間外超過 is 2
		return this.attendanceTypeFinder.getItemByScreenUseAtr(2);
	} 
	
}
