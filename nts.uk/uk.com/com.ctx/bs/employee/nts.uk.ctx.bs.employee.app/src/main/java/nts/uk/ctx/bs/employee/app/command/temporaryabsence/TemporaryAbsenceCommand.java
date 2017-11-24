package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class TemporaryAbsenceCommand {

	
	/**社員ID*/
	@PeregEmployeeId
	private String employeeId;
	
	/**休職休業ID*/
	@PeregRecordId
	private String histoyId;
	
	@PeregItem("")
	private int leaveHolidayAtr;
	
	@PeregItem("")
	private String remarks;
	
	@PeregItem("")
	private int soInsPayCategory;
	
	// -------------- extend object ----------------------
	@PeregItem("")
	private boolean multiple;

	@PeregItem("")
	private String familyMemberId;
	
	@PeregItem("")
	private boolean sameFamily;
	
	@PeregItem("")
	private int childType;

	@PeregItem("")
	private GeneralDate createDate;

	@PeregItem("")
	private boolean spouseIsLeave;
	
	@PeregItem("")
	private int sameFamilyDays;
	
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;
}
