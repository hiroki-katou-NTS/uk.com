package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemTemporaryAbsence extends CtgItemFixDto{
	/**
	 *  休職休業
	 */
	private String employeeId;
	/**休職休業ID*/
	private String tempAbsenceId;
	/**temporary absence state*/
	private int tempAbsenceType;
	/**Start date*/
	private GeneralDate strD;
	/**End date*/
	private GeneralDate endD;
	/**理由 reason*/
	private String tempAbsenceReason;
	/**家族メンバーId Family member id*/
	private String familyMemberId;
	/**出産日 birth date*/
	private GeneralDate birthDate;
	/**多胎妊娠区分 Multiple pregnancy segment*/
	private int mulPregnancySegment;
	
	private ItemTemporaryAbsence(String employeeId, String tempAbsenceId, int tempAbsenceType, GeneralDate strD, GeneralDate endD, 
			String tempAbsenceReason, String familyMemberId, GeneralDate birthDate, int mulPregnancySegment){
		super();
		this.ctgItemType = CtgItemType.TEMPORARY_ABSENCE;
		this.employeeId = employeeId;
		this.tempAbsenceId = tempAbsenceId;
		this.tempAbsenceType = tempAbsenceType;
		this.strD = strD;
		this.endD = endD;
		this.tempAbsenceReason = tempAbsenceReason;
		this.familyMemberId = familyMemberId;
		this.birthDate = birthDate;
		this.mulPregnancySegment = mulPregnancySegment;	
	}
	
	public static ItemTemporaryAbsence createFromJavaType(String employeeId, String tempAbsenceId, int tempAbsenceType, GeneralDate strD, GeneralDate endD, 
			String tempAbsenceReason, String familyMemberId, GeneralDate birthDate, int mulPregnancySegment){
		return new ItemTemporaryAbsence(employeeId, tempAbsenceId, tempAbsenceType, strD, endD, tempAbsenceReason, familyMemberId, birthDate, mulPregnancySegment);
	}
}
