package nts.uk.ctx.at.shared.dom.worktype.absenceframe;

import java.util.UUID;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;

import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

@Getter
public class AbsenceFrame {
	
	/*会社ID*/
	private String companyId;
	/*欠勤枠ID*/
	private UUID absenceFrameId;
	/*枠名称*/
	private WorkTypeName absenceFrameName;
	/*欠勤枠の廃止区分*/
	private DeprecateClassification deprecateAbsence;
	
	
	public AbsenceFrame(String companyId, UUID absenceFrameId, WorkTypeName absenceFrameName,
			DeprecateClassification deprecateAbsence) {
		super();
		this.companyId = companyId;
		this.absenceFrameId = absenceFrameId;
		this.absenceFrameName = absenceFrameName;
		this.deprecateAbsence = deprecateAbsence;
	}
	
	public static AbsenceFrame createSimpleFromJavaType(String companyId, String absenceFrameId, String absenceFrameName,
			int deprecateAbsence) {
		return new AbsenceFrame(
				companyId, 
				UUID.fromString(absenceFrameId), 
				new WorkTypeName(absenceFrameName), 
				EnumAdaptor.valueOf(deprecateAbsence, DeprecateClassification.class));
	}
}
