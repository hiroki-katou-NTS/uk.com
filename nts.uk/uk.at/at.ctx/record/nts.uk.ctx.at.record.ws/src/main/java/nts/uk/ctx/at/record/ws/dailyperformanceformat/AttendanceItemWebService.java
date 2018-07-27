/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttdItemLinkRequest;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttendanceItemsFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
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
	
	@POST
	@Path("attendanceItem/getAttendanceItems")
	public List<AttdItemDto> getAttendanceItems(){
		return this.attendanceItemsFinder.findAll();
	}
	
	@POST
	@Path("attendanceItem/getMonthlyAttendanceItems")
	public List<AttdItemDto> getMonthlyAttendanceItems(){
		return this.attendanceItemsFinder.findAllMonthly();
	}
	
	@POST
	@Path("attendanceItem/getListByAttendanceAtr/{dailyAttendanceAtr}")
	public List<AttdItemDto> getListByAttendanceAtr(@PathParam("dailyAttendanceAtr") int dailyAttendanceAtr){
		return this.attendanceItemsFinder.findListByAttendanceAtr(dailyAttendanceAtr);
	}
	
	/**
	 * added by HungTT
	 * @param monthlyAttendanceAtr
	 * @return
	 */
	@POST
	@Path("attendanceItem/getListMonthlyByAttendanceAtr/{monthlyAttendanceAtr}")
	public List<AttdItemDto> getListMonthlyByAttendanceAtr(@PathParam("monthlyAttendanceAtr") int monthlyAttendanceAtr){
		return this.attendanceItemsFinder.findListMonthlyByAttendanceAtr(monthlyAttendanceAtr);
	}
	/**
	 * added by HungTT
	 * @param monthlyAttendanceAtr
	 * @return
	 */
	@POST
	@Path("attendanceItem/getListMonthlyByAtrPrimitive/{monthlyAttendanceAtr}")
	public List<AttdItemDto> getListMonthlyByAtrPrimitive(@PathParam("monthlyAttendanceAtr") int monthlyAttendanceAtr){
		return this.attendanceItemsFinder.findListMonthlyByAtrPrimitive(monthlyAttendanceAtr);
	}

	/**
	 * Gets the attd item linking by any item.
	 *
	 * @param request the request
	 * @return the attd item linking by any item
	 * 
	 * @author anhnm
	 */
	@POST
	@Path("attendanceItem/daily/findbyanyitem")
	public List<AttdItemDto> getAttdItemLinkingByAnyItem(AttdItemLinkRequest request) {
		return this.attendanceItemsFinder.findByAnyItem(request);
	}

}
