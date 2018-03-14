package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
@AllArgsConstructor
@Setter
@Getter
public class ReflectScheDto {
	/**社員ID	 */
	private String employeeId;
	/**	年月日 */
	private GeneralDate datePara;
	/**	実行種別 */
	private ExecutionType executionType;
	/** 申請内容	 */
	private Application_New application;
	/** 振出・休出時反映する区分	 */
	private boolean reflectAtr;
	/**	時刻の反映*/
	private ApplyTimeRequestAtr timeAtr;
	/**	直行直帰申請 */
	private GoBackDirectly goBackDirectly;
	/**
	 * 休暇申請
	 */
	private AppAbsence forLeave;
}
