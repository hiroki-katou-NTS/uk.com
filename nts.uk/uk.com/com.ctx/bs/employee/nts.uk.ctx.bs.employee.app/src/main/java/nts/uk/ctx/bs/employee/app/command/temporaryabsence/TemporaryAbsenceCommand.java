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
	
	@PeregItem("IS00090")
	private int leaveHolidayAtr;
	
	/** 備考 */
	@PeregItem("IS00099")
	private String remarks;
	
	@PeregItem("IS00098")
	private int soInsPayCategory;
	
	// -------------- extend object ----------------------
	@PeregItem("")
	private boolean multiple;

	@PeregItem("")
	private String familyMemberId;
	
	@PeregItem("IS00091")
	private boolean sameFamily;
	
	@PeregItem("IS00094")
	private int childType;

	@PeregItem("IS00095")
	private GeneralDate createDate;

	@PeregItem("IS00097")
	private boolean spouseIsLeave;
	
	@PeregItem("IS00092")
	private int sameFamilyDays;
	
	@PeregItem("IS00088")
	private GeneralDate startDate;
	
	@PeregItem("IS00089")
	private GeneralDate endDate;
}
