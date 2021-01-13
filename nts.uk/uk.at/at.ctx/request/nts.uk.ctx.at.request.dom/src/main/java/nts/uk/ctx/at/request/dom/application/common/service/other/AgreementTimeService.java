package nts.uk.ctx.at.request.dom.application.common.service.other;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.OverTimeWorkHoursOutput;

/**
 * 17.３６時間の表示
 * @author Doan Duy Hung
 *
 */
public interface AgreementTimeService {
	
	public AgreeOverTimeOutput getAgreementTime(String companyID, String employeeID);
	
	/**
	 * Refactor5 17.３６時間の表示
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム."17.３６時間の表示"
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	public OverTimeWorkHoursOutput getOverTimeWorkHoursOutput(String companyId, String employeeId);
	
}
