package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class UpdateTemporaryAbsenceCommand {
	
	/**社員ID*/
	private String employeeId;
	
	/**休職休業ID*/
	private String tempAbsenceId;
	
	/**temporary absence state*/
	@PeregItem("")
	private int tempAbsenceType;
	
	private String histID;
	
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;
	
	/**理由 reason*/
	@PeregItem("")
	private String tempAbsenceReason;
	
	/**家族メンバーId Family member id*/
	@PeregItem("")
	private String familyMemberId;
	
	/**出産日 birth date*/
	@PeregItem("")
	private GeneralDate birthDate;
	
	/**多胎妊娠区分 Multiple pregnancy segment*/
	@PeregItem("")
	private int mulPregnancySegment;
}
