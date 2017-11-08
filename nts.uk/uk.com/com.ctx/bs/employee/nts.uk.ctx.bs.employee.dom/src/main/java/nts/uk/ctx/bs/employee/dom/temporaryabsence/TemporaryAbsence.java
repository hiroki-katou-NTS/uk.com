package nts.uk.ctx.bs.employee.dom.temporaryabsence;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 	休職休業
 * @author xuan vinh
 * */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryAbsence extends AggregateRoot{
	/**社員ID*/
	private String employeeId;
	/**休職休業ID*/
	private String tempAbsenceId;
	/**temporary absence state*/
	private TempAbsenceType tempAbsenceType;
	/**Start date*/
	private GeneralDate startDate;
	/**End date*/
	private GeneralDate endDate;
	/**理由 reason*/
	private String tempAbsenceReason;
	/**家族メンバーId Family member id*/
	private String familyMemberId;
	/**出産日 birth date*/
	private GeneralDate birthDate;
	/**多胎妊娠区分 Multiple pregnancy segment*/
	private int mulPregnancySegment;
	
	public static TemporaryAbsence createSimpleFromJavaType(String employeeId, String tempAbsenceId, int tempAbsenceType,
			GeneralDate startDate, GeneralDate endDate, String tempAbsenceReason, String familyMemberId, GeneralDate birthDate, int  mulPregnancySegment){
		return new TemporaryAbsence(employeeId, tempAbsenceId, EnumAdaptor.valueOf(tempAbsenceType, TempAbsenceType.class), 
				startDate, endDate, tempAbsenceReason, familyMemberId,birthDate, mulPregnancySegment);
	}
}
