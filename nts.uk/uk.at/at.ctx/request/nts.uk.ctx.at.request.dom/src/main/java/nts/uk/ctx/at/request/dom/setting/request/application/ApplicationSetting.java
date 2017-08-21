package nts.uk.ctx.at.request.dom.setting.request.application;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.AppCanAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.PossibleAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RetrictDay;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RetrictPreTimeDay;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.VacationAppType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

@Value
public class ApplicationSetting {
	private String companyID;

	private ApplicationType appType;

	private AppDisplayAtr prePostInitFlg;

	private AppCanAtr prePostCanChangeFlg;

	private AppDisplayAtr typicalReasonDisplayFlg;

	private AppCanAtr sendMailWhenApprovalFlg;

	private AppCanAtr sendMailWhenRegisterFlg;

	private AppCanAtr displayReasonFlg;

	private VacationAppType vacationAppType;

	private AppCanAtr appActLockFlg;

	private AppCanAtr appEndWorkFlg;

	private AppCanAtr appActConfirmFlg;

	private AppCanAtr appOvertimeNightFlg;

	private AppCanAtr appActMonthConfirmFlg;

	private RequiredFlg requireAppReasonFlg;

	private CheckMethod retrictPreMethodFlg;

	private UseAtr retrictPreUseFlg;

	private RetrictDay retrictPreDay;
	
	private RetrictPreTimeDay retrictPreTimeDay;

	private PossibleAtr retrictPreCanAceeptFlg;

	private AllowAtr retrictPostAllowFutureFlg;

	private AppDisplayAtr displayPrePostFlg;

	private AppDisplayAtr displaySearchTimeFlg;

	private RetrictDay displayInitDayFlg;

	public ApplicationSetting(String companyID, ApplicationType appType, AppDisplayAtr prePostInitFlg,
			AppCanAtr prePostCanChangeFlg, AppDisplayAtr typicalReasonDisplayFlg, AppCanAtr sendMailWhenApprovalFlg,
			AppCanAtr sendMailWhenRegisterFlg, AppCanAtr displayReasonFlg, VacationAppType vacationAppType,
			AppCanAtr appActLockFlg, AppCanAtr appEndWorkFlg, AppCanAtr appActConfirmFlg, AppCanAtr appOvertimeNightFlg,
			AppCanAtr appActMonthConfirmFlg, RequiredFlg requireAppReasonFlg, CheckMethod retrictPreMethodFlg,
			UseAtr retrictPreUseFlg, RetrictDay retrictPreDay, RetrictPreTimeDay retrictPreTimeDay,
			PossibleAtr retrictPreCanAceeptFlg, AllowAtr retrictPostAllowFutureFlg, AppDisplayAtr displayPrePostFlg,
			AppDisplayAtr displaySearchTimeFlg, RetrictDay displayInitDayFlg) {
		super();
		this.companyID = companyID;
		this.appType = appType;
		this.prePostInitFlg = prePostInitFlg;
		this.prePostCanChangeFlg = prePostCanChangeFlg;
		this.typicalReasonDisplayFlg = typicalReasonDisplayFlg;
		this.sendMailWhenApprovalFlg = sendMailWhenApprovalFlg;
		this.sendMailWhenRegisterFlg = sendMailWhenRegisterFlg;
		this.displayReasonFlg = displayReasonFlg;
		this.vacationAppType = vacationAppType;
		this.appActLockFlg = appActLockFlg;
		this.appEndWorkFlg = appEndWorkFlg;
		this.appActConfirmFlg = appActConfirmFlg;
		this.appOvertimeNightFlg = appOvertimeNightFlg;
		this.appActMonthConfirmFlg = appActMonthConfirmFlg;
		this.requireAppReasonFlg = requireAppReasonFlg;
		this.retrictPreMethodFlg = retrictPreMethodFlg;
		this.retrictPreUseFlg = retrictPreUseFlg;
		this.retrictPreDay = retrictPreDay;
		this.retrictPreTimeDay = retrictPreTimeDay;
		this.retrictPreCanAceeptFlg = retrictPreCanAceeptFlg;
		this.retrictPostAllowFutureFlg = retrictPostAllowFutureFlg;
		this.displayPrePostFlg = displayPrePostFlg;
		this.displaySearchTimeFlg = displaySearchTimeFlg;
		this.displayInitDayFlg = displayInitDayFlg;
	}
	
	
}
