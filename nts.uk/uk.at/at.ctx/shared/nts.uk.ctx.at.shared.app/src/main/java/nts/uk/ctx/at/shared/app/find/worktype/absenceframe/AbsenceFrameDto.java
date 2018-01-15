package nts.uk.ctx.at.shared.app.find.worktype.absenceframe;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class AbsenceFrameDto {
	/*会社ID*/
	private String companyId;
	
	/*欠勤枠ID*/
	private int absenceFrameNo;
	
	/*枠名称*/
	private String absenceFrameName;
	
	/*欠勤枠の廃止区分*/
	private int deprecateAbsence;
	
	/**
	 * From domain method
	 * 
	 */
	public static AbsenceFrameDto fromDomain(AbsenceFrame domain){
		return new AbsenceFrameDto(
			domain.getCompanyId(),
			domain.getAbsenceFrameNo(),
			domain.getAbsenceFrameName().v(),
			domain.getDeprecateAbsence().value
		);
	}
}
