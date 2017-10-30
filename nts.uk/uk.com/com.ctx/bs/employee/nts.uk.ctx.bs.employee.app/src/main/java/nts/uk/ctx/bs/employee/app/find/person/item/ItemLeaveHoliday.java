package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemLeaveHoliday extends CtgItemFixDto{
	/**
	 *  休職休業
	 */
	/** 社員ID */
	private String sid;
	/** 休職休業ID */
	private String leaveHolidayId;
	
	private GeneralDate strD;
	private GeneralDate endD;
	private int leaveHolidayType;
	private String familyMemberId;
	private String reasonLeaveHoliday;
	private boolean midweekCloseMultiple;
	private GeneralDate midweekBirthDate;
	
	private ItemLeaveHoliday(String sid, String leaveHolidayId, GeneralDate strD, GeneralDate endD, int leaveHolidayType,
			String familyMemberId, String reasonLeaveHoliday,  boolean midweekCloseMultiple, GeneralDate midweekBirthDate){
		super();
		this.ctgItemType = CtgItemType.LEAVE_HOLIDAY;
		this.sid = sid;
		this.leaveHolidayId = leaveHolidayId;
		this.strD = strD;
		this.endD = endD;
		this.leaveHolidayType = leaveHolidayType;
		this.familyMemberId = familyMemberId;
		this.reasonLeaveHoliday = reasonLeaveHoliday;
		this.midweekCloseMultiple = midweekCloseMultiple;
		this.midweekBirthDate = midweekBirthDate;
		
	}
	
	public static ItemLeaveHoliday createFromJavaType(String sid, String leaveHolidayId, GeneralDate strD, GeneralDate endD, int leaveHolidayType,
			String familyMemberId, String reasonLeaveHoliday,  boolean midweekCloseMultiple, GeneralDate midweekBirthDate){
		return new ItemLeaveHoliday(sid, leaveHolidayId, strD, endD, leaveHolidayType, familyMemberId, reasonLeaveHoliday, midweekCloseMultiple, midweekBirthDate);
	}
}
