package nts.uk.ctx.at.function.ws.alarm.checkcondition;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.TargetSelectionRange;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.TargetServiceType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.YearCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CheckTimeType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.DaiCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.TimeZoneTargetRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.MonCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfContrast;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfVacations;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.WeeklyCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;

/**
 * Class Kal003bWebService
 * @author HieuNV
 *
 */
@Path("at/function/alarm/checkcondition/kal003b")
@Produces("application/json")
public class Kal003bWebService extends WebService{
	
	@POST
	@Path("getEnumSingleValueCompareTypse")
	public List<EnumConstant> getEnumSingleValueCompareType(){

	    return EnumAdaptor.convertToValueNameList(SingleValueCompareType.class, 
	            SingleValueCompareType.NOT_EQUAL,
	            SingleValueCompareType.EQUAL,
	            SingleValueCompareType.LESS_OR_EQUAL,
	            SingleValueCompareType.GREATER_OR_EQUAL,
	            SingleValueCompareType.LESS_THAN,
	            SingleValueCompareType.GREATER_THAN);
	} 
	
	@POST
	@Path("getEnumRangeCompareType")
    public List<EnumConstant> getEnumRangeCompareType(){

        return EnumAdaptor.convertToValueNameList(RangeCompareType.class, 
                RangeCompareType.BETWEEN_RANGE_OPEN,
                RangeCompareType.BETWEEN_RANGE_CLOSED,
                RangeCompareType.OUTSIDE_RANGE_OPEN,
                RangeCompareType.OUTSIDE_RANGE_CLOSED);
        
        
    }

	@POST
	@Path("getEnumTypeCheckWorkRecord")
    public List<EnumConstant> getEnumTypeCheckWorkRecord(){

        return EnumAdaptor.convertToValueNameList(TypeCheckWorkRecord.class, 
                TypeCheckWorkRecord.TIME,
                TypeCheckWorkRecord.TIMES,
                TypeCheckWorkRecord.AMOUNT_OF_MONEY,
                TypeCheckWorkRecord.TIME_OF_DAY,
                TypeCheckWorkRecord.CONTINUOUS_TIME,
                TypeCheckWorkRecord.CONTINUOUS_WORK,
                TypeCheckWorkRecord.CONTINUOUS_TIME_ZONE,
                TypeCheckWorkRecord.CONTINUOUS_CONDITION);

    }
	//MinhVV Edit
	@POST
	@Path("get-enum-type-check-work-record-multiple-month")
    public List<EnumConstant> getEnumTypeCheckWorkRecordMultipleMonth(){

        return EnumAdaptor.convertToValueNameList(TypeCheckWorkRecordMultipleMonth.class, 
        		TypeCheckWorkRecordMultipleMonth.TIME,
        		TypeCheckWorkRecordMultipleMonth.DAYS,
        		TypeCheckWorkRecordMultipleMonth.AMOUNT,
        		TypeCheckWorkRecordMultipleMonth.TIMES,
        		TypeCheckWorkRecordMultipleMonth.AVERAGE_TIME,
        		TypeCheckWorkRecordMultipleMonth.AVERAGE_DAYS,
        		TypeCheckWorkRecordMultipleMonth.AVERAGE_AMOUNT,
        		TypeCheckWorkRecordMultipleMonth.AVERAGE_TIMES,
        		TypeCheckWorkRecordMultipleMonth.CONTINUOUS_TIME,
        		TypeCheckWorkRecordMultipleMonth.CONTINUOUS_DAYS,
        		TypeCheckWorkRecordMultipleMonth.CONTINUOUS_AMOUNT, 
        		TypeCheckWorkRecordMultipleMonth.CONTINUOUS_TIMES,
        		TypeCheckWorkRecordMultipleMonth.NUMBER_TIME,
        		TypeCheckWorkRecordMultipleMonth.NUMBER_DAYS,
        		TypeCheckWorkRecordMultipleMonth.NUMBER_AMOUNT,
        		TypeCheckWorkRecordMultipleMonth.NUMBER_TIMES);

    }
	
	@POST
	@Path("getEnumTargetSelectionRange")
    public List<EnumConstant> getEnumTargetSelectionRange(){

        return EnumAdaptor.convertToValueNameList(TargetSelectionRange.class, 
                TargetSelectionRange.SELECTION,
                TargetSelectionRange.OTHER_SELECTION);
    }
	
	@POST
	@Path("getEnumTargetServiceType")
    public List<EnumConstant> getEnumTargetServiceType(){

        return EnumAdaptor.convertToValueNameList(TargetServiceType.class, 
                TargetServiceType.ALL,
                TargetServiceType.SELECTION,
                TargetServiceType.OTHER_SELECTION);
    }
	
	@POST
    @Path("getEnumLogicalOperator")
    public List<EnumConstant> getEnumLogicalOperator(){

        return EnumAdaptor.convertToValueNameList(LogicalOperator.class, 
                LogicalOperator.AND,
                LogicalOperator.OR);
    }
	
	@POST
	@Path("getEnumDaiCheckItemType")
	public List<EnumConstant> getEunumDaiCheckItemType() {
		return EnumAdaptor.convertToValueNameList(DaiCheckItemType.class,
				DaiCheckItemType.TIME,
				DaiCheckItemType.CONTINUOUS_TIME,
				DaiCheckItemType.CONTINUOUS_WORK,
				DaiCheckItemType.CONTINUOUS_TIMEZONE);
	}
	
	@POST
	@Path("getCheckTimeType")
	public List<EnumConstant> getCheckTimeType() {
		return EnumAdaptor.convertToValueNameList(CheckTimeType.class, CheckTimeType.RESERVATION_TIME);
	}
	
	@POST
	@Path("getTimeZoneTargetRange")
	public List<EnumConstant> getTimeZoneTargetRange() {
		return EnumAdaptor.convertToValueNameList(TimeZoneTargetRange.class, TimeZoneTargetRange.CHOICE, TimeZoneTargetRange.OTHER);
	}
	
	@POST
	@Path("getEnumMonCheckItemType")
	public List<EnumConstant> getEunumMonCheckItemType() {
		return EnumAdaptor.convertToValueNameList(MonCheckItemType.class,
				MonCheckItemType.CONTRAST,
				MonCheckItemType.TIME,
				MonCheckItemType.NUMBER_DAYS,
				MonCheckItemType.REMAIN_NUMBER);
	}
	
	@POST
	@Path("getEnumTypeOfDays")
	public List<EnumConstant> getEnumTypeOfDays() {
		return EnumAdaptor.convertToValueNameList(TypeOfDays.class);
	}
	
	@POST
	@Path("getEnumTypeOfTime")
	public List<EnumConstant> getEnumTypeOfTime() {
		return EnumAdaptor.convertToValueNameList(TypeOfTime.class);
	}
	
	@POST
	@Path("getEnumTypeOfVacations")
	public List<EnumConstant> getEnumTypeOfVacations() {
		return EnumAdaptor.convertToValueNameList(TypeOfVacations.class);
	}
	
	@POST
	@Path("getEnumTypeOfContrast")
	public List<EnumConstant> getEnumTypeOfContrast() {
		return EnumAdaptor.convertToValueNameList(TypeOfContrast.class);
	}
	
	@POST
	@Path("getEnumYearCheckItemType")
	public List<EnumConstant> getEunumYearCheckItemType() {
		return EnumAdaptor.convertToValueNameList(YearCheckItemType.class);
	}
	
	@POST
	@Path("getEnumWeeklyCheckItemType")
	public List<EnumConstant> getEnumWeeklyCheckItemType() {
		return EnumAdaptor.convertToValueNameList(WeeklyCheckItemType.class);
	}
}