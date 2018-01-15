package nts.uk.ctx.at.shared.app.command.worktype.absenceframe;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class AbsenceFrameCommand {
	/*会社ID*/
	private String companyId;
	
	/*欠勤枠ID*/
	private int absenceFrameNo;
	
	/*枠名称*/
	private String absenceFrameName;
	
	/*欠勤枠の廃止区分*/
	private int deprecateAbsence;

	/**
	 * Convert to domain object
	 * @return
	 */
	public AbsenceFrame toDomain() {
		String companyId = AppContexts.user().companyId();
		
		return  AbsenceFrame.createFromJavaType(companyId, absenceFrameNo, absenceFrameName, deprecateAbsence);
	}
}
