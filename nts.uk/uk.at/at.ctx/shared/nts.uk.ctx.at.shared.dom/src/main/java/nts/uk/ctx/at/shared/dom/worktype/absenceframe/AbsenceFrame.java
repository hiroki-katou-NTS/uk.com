package nts.uk.ctx.at.shared.dom.worktype.absenceframe;



import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

/**
 * 欠勤枠
 * 
 * @author sonnh
 *
 */
@Getter
@NoArgsConstructor
public class AbsenceFrame {
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 欠勤枠ID
	 */
	private int absenceFrameNo;
	
	/**
	 * 枠名称
	 */
	private WorkTypeName absenceFrameName;
	
	/**
	 * 欠勤枠の廃止区分
	 */
	private DeprecateClassification deprecateAbsence;
	
	/**
	 * 
	 * @param companyId
	 * @param absenceFrameNo
	 * @param absenceFrameName
	 * @param deprecateAbsence
	 */
	public AbsenceFrame(String companyId, int absenceFrameNo, WorkTypeName absenceFrameName,
			DeprecateClassification deprecateAbsence) {
		super();
		this.companyId = companyId;
		this.absenceFrameNo = absenceFrameNo;
		this.absenceFrameName = absenceFrameName;
		this.deprecateAbsence = deprecateAbsence;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param absenceFrameNo
	 * @param absenceFrameName
	 * @param deprecateAbsence
	 * @return
	 */
	public static AbsenceFrame createSimpleFromJavaType(String companyId, int absenceFrameNo, String absenceFrameName,
			int deprecateAbsence) {
		return new AbsenceFrame(
				companyId, 
				absenceFrameNo, 
				new WorkTypeName(absenceFrameName), 
				EnumAdaptor.valueOf(deprecateAbsence, DeprecateClassification.class));
	}

	/**
	 * 
	 * @param companyId
	 * @param absenceFrameNo
	 * @param absenceFrameName
	 * @param deprecateAbsence
	 * @return
	 */
	public static AbsenceFrame createFromJavaType(String companyId, int absenceFrameNo, String absenceFrameName,
			int deprecateAbsence) {
		return new AbsenceFrame(companyId, absenceFrameNo, new WorkTypeName(absenceFrameName), EnumAdaptor.valueOf(deprecateAbsence, DeprecateClassification.class));
	}
}
