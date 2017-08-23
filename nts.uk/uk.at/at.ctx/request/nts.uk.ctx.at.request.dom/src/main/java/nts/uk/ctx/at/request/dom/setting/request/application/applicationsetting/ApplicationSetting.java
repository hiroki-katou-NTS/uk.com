package nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting;

import lombok.Value;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AprovalPersonFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.NumDaysOfWeek;
import nts.uk.ctx.at.request.dom.setting.request.application.common.PriorityFLg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.ReflectionFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RetrictDay;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

@Value
public class ApplicationSetting {
	private String companyID;

	private AppCanAtr appActLockFlg;

	private AppCanAtr appEndWorkFlg;

	private AppCanAtr appActConfirmFlg;

	private AppCanAtr appOvertimeNightFlg;

	private AppCanAtr appActMonthConfirmFlg;

	private RequiredFlg requireAppReasonFlg;

	private AppDisplayAtr displayPrePostFlg;

	private AppDisplayAtr displaySearchTimeFlg;

	private RetrictDay displayInitDayFlg;

	/** 承認 */

	private BaseDateFlg baseDateFlg;

	private AppDisplayAtr advanceExcessMessDispAtr;

	private AppDisplayAtr hwAdvanceDispAtr;

	private AppDisplayAtr hwActualDispAtr;

	private AppDisplayAtr actualExcessMessDispAtr;

	private AppDisplayAtr otAdvanceDispAtr;

	private AppDisplayAtr otActualDispAtr;

	private NumDaysOfWeek warningDateDispAtr;

	private AppDisplayAtr appReasonDispAtr;

	private AppCanAtr appContentChangeFlg;

	private AprovalPersonFlg personApprovalFlg;

	private ReflectionFlg scheReflectFlg;

	private PriorityFLg priorityTimeReflectFlg;

	private ReflectionFlg attendentTimeReflectFlg;

	public ApplicationSetting(String companyID, AppCanAtr appActLockFlg, AppCanAtr appEndWorkFlg,
			AppCanAtr appActConfirmFlg, AppCanAtr appOvertimeNightFlg, AppCanAtr appActMonthConfirmFlg,
			RequiredFlg requireAppReasonFlg, AppDisplayAtr displayPrePostFlg, AppDisplayAtr displaySearchTimeFlg,
			RetrictDay displayInitDayFlg, BaseDateFlg baseDateFlg, AppDisplayAtr advanceExcessMessDispAtr,
			AppDisplayAtr hwAdvanceDispAtr, AppDisplayAtr hwActualDispAtr, AppDisplayAtr actualExcessMessDispAtr,
			AppDisplayAtr otAdvanceDispAtr, AppDisplayAtr otActualDispAtr, NumDaysOfWeek warningDateDispAtr,
			AppDisplayAtr appReasonDispAtr, AppCanAtr appContentChangeFlg, AprovalPersonFlg personApprovalFlg,
			ReflectionFlg scheReflectFlg, PriorityFLg priorityTimeReflectFlg, ReflectionFlg attendentTimeReflectFlg) {
		this.companyID = companyID;
		this.appActLockFlg = appActLockFlg;
		this.appEndWorkFlg = appEndWorkFlg;
		this.appActConfirmFlg = appActConfirmFlg;
		this.appOvertimeNightFlg = appOvertimeNightFlg;
		this.appActMonthConfirmFlg = appActMonthConfirmFlg;
		this.requireAppReasonFlg = requireAppReasonFlg;
		this.displayPrePostFlg = displayPrePostFlg;
		this.displaySearchTimeFlg = displaySearchTimeFlg;
		this.displayInitDayFlg = displayInitDayFlg;
		this.baseDateFlg = baseDateFlg;
		this.advanceExcessMessDispAtr = advanceExcessMessDispAtr;
		this.hwAdvanceDispAtr = hwAdvanceDispAtr;
		this.hwActualDispAtr = hwActualDispAtr;
		this.actualExcessMessDispAtr = actualExcessMessDispAtr;
		this.otAdvanceDispAtr = otAdvanceDispAtr;
		this.otActualDispAtr = otActualDispAtr;
		this.warningDateDispAtr = warningDateDispAtr;
		this.appReasonDispAtr = appReasonDispAtr;
		this.appContentChangeFlg = appContentChangeFlg;
		this.personApprovalFlg = personApprovalFlg;
		this.scheReflectFlg = scheReflectFlg;
		this.priorityTimeReflectFlg = priorityTimeReflectFlg;
		this.attendentTimeReflectFlg = attendentTimeReflectFlg;
	}

}
