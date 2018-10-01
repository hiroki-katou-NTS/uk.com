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
        		TypeCheckWorkRecordMultipleMonth.TIMES,
        		TypeCheckWorkRecordMultipleMonth.AMOUNT,
        		TypeCheckWorkRecordMultipleMonth.AVERAGE_TIME,
        		TypeCheckWorkRecordMultipleMonth.AVERAGE_TIMES,
        		TypeCheckWorkRecordMultipleMonth.AVERAGE_AMOUNT,
        		TypeCheckWorkRecordMultipleMonth.CONTINUOUS_TIME,
        		TypeCheckWorkRecordMultipleMonth.CONTINUOUS_TIMES,
        		TypeCheckWorkRecordMultipleMonth.CONTINUOUS_AMOUNT, 
        		TypeCheckWorkRecordMultipleMonth.NUMBER_TIME,
        		TypeCheckWorkRecordMultipleMonth.NUMBER_TIMES,
        		TypeCheckWorkRecordMultipleMonth.NUMBER_AMOUNT);

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
}
