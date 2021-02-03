/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.dailyattendance;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.DailyAttendanceItemFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.ErrorAlarmWorkRecordDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.SimpleWorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;

/**
 * The Class DailyAttendanceItemWebService.
 */
@Path("at/record/attendanceitem/daily")
@Produces("application/json")
public class DailyAttendanceItemWebService extends WebService {

	/** The finder. */
	@Inject
	private DailyAttendanceItemFinder finder;

	/**
	 * Find by any item.
	 *
	 * @param request the request
	 * @return the list
	 */
	@POST
	@Path("getattendcomparison/{checkItem}")
	public List<AttdItemDto> getDailyAttendanceComparisonBy(@PathParam("checkItem") int checkItem) {
	    DailyAttendanceAtr dailyAttendanceAtr = convertCheckItemToDailyAttendanceAtr(checkItem);
	    if (dailyAttendanceAtr == null) {
	        return new ArrayList<>();
	    }
		return this.finder.findDailyAttendanceItemBy(dailyAttendanceAtr);
	}
	
	@POST
    @Path("getlistattendcomparison")
    public List<AttdItemDto> getDailyAttendance() {
		List<Integer> dailyAttendanceAtrs = new ArrayList<>();
		dailyAttendanceAtrs.add(DailyAttendanceAtr.NumberOfTime.value);
		dailyAttendanceAtrs.add(DailyAttendanceAtr.AmountOfMoney.value);
		dailyAttendanceAtrs.add(DailyAttendanceAtr.Time.value);
		dailyAttendanceAtrs.add(DailyAttendanceAtr.TimeOfDay.value);
		
        return this.finder.findDailyAttendance(dailyAttendanceAtrs);
    }
	
    @POST
    @Path("getattendcoutinoustime")
    public List<AttdItemDto> getDailyAttendanceContinuesTimeBy() {
       //check item as time
        return this.finder.findDailyAttendanceItemBy(DailyAttendanceAtr.Time);
    }
    
    
    @POST
    @Path("getattendcoutinouswork")
    public List<WorkTypeDto> getDailyAttendanceContinuesWorkBy() {
       //check item as time
        return this.finder.findDailyAttendanceContinousWorkType();
    }
    
    @POST
    @Path("getattendcoutinoustimezone")
    public List<SimpleWorkTimeSettingDto> getDailyAttendanceContinuesTimeZoneBy() {
       //check item as time
        return this.finder.findDailyAttendanceContinuesTimeZoneBy();
    }
    
    @POST
    @Path("getattendcompound/{eralCheckId}")
    public List<AttdItemDto> getDailyAttendanceCompoundBy(@PathParam("eralCheckId") int eralCheckId) {
       //check item as time
        return this.finder.findDailyAttendanceCompoundBy(eralCheckId);
    }
    
	@POST
    @Path("getattendnamebyids")
    public List<DailyAttendanceItemNameAdapterDto> getAttendNameByIds(List<Integer> dailyAttendanceItemIds) {
        return this.finder.getDailyAttendanceItemName(dailyAttendanceItemIds);
    }
	
	@POST
    @Path("geterroralarmcondition/{eralCheckId}")
    public ErrorAlarmWorkRecordDto getErrorAlarmCategoryAndCondition(@PathParam("eralCheckId") String eralCheckId) {
        return this.finder.getErrorAlarmCategoryAndCondition(eralCheckId);
    }
	
	/**
	 * convert from 勤務実績のチェック項目の種類 -> 日次勤怠項目の属性
	 * @param checkItem
	 */
	private DailyAttendanceAtr convertCheckItemToDailyAttendanceAtr(int checkItem) {
	    if (checkItem == TypeCheckWorkRecord.TIME.value) {
	        return DailyAttendanceAtr.Time;
	    } else if (checkItem == TypeCheckWorkRecord.TIMES.value) {
	        return DailyAttendanceAtr.NumberOfTime;
	    } else if (checkItem == TypeCheckWorkRecord.AMOUNT_OF_MONEY.value) {
            return DailyAttendanceAtr.AmountOfMoney;
        } else if (checkItem == TypeCheckWorkRecord.TIME_OF_DAY.value) {
            return DailyAttendanceAtr.TimeOfDay;
        }
	    return null;
	}
}
