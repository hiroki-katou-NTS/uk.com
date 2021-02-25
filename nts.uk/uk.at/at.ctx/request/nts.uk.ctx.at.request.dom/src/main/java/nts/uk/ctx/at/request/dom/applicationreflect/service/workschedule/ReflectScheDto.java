package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork_Old;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;
import nts.uk.ctx.at.request.dom.applicationreflect.service.InformationSettingOfEachApp;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionTypeExImport;
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ReflectScheDto {
	/**社員ID	 */
	private String employeeId;
	/**	年月日 */
	private GeneralDate datePara;
	/**	実行種別 */
	private ExecutionTypeExImport executionType;
	/** 振出・休出時反映する区分	 */
	private boolean reflectAtr;
	/**	時刻の反映*/
	private ApplyTimeRequestAtr timeAtr;
	private Application appInfor;
	/**	直行直帰申請 */
	private GoBackDirectly_Old goBackDirectly;
	/**
	 * 休暇申請
	 */
	private AppAbsence forLeave;
	/**
	 * 勤務変更申請
	 */
	private AppWorkChange_Old workChange;
	/**
	 * 	休日出勤申請
	 */
	private AppHolidayWork_Old holidayWork;
	/**
	 * 振休申請
	 */
	private AbsenceLeaveApp absenceLeave;
	/**
	 * 振出申請
	 */
	private RecruitmentApp recruitment;
	
	private InformationSettingOfEachApp reflectSetting;
}
