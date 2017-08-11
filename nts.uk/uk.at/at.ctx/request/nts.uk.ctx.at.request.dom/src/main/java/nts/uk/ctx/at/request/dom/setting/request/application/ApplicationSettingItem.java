package nts.uk.ctx.at.request.dom.setting.request.application;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.CanAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.PossibleAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RetrictDay;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RetrictPreTimeDay;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.VacationAppType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

@Value
public class ApplicationSettingItem {
	private String companyID;

	private ApplicationType appType;

	private AppDisplayAtr prePostInitFlg;

	private CanAtr prePostCanChangeFlg;

	private AppDisplayAtr typicalReasonDisplayFlg;

	private CanAtr sendMailWhenApprovalFlg;

	private CanAtr sendMailWhenRegisterFlg;

	private CanAtr displayReasonFlg;

	private VacationAppType vacationAppType;

	private CanAtr appActLockFlg;

	private CanAtr appEndWorkFlg;

	private CanAtr appActConfirmFlg;

	private CanAtr appOvertimeNightFlg;

	private CanAtr appActMonthConfirmFlg;

	private RequiredFlg requiredAppReasonFlg;

	private CheckMethod retrictPreMethodFlg;

	private UseAtr retrictPreUseFlg;

	private RetrictDay retrictPreDay;

	private RetrictPreTimeDay retrictPreTimeDay;

	private PossibleAtr rertricPreCanAceeptFlg;

	private AllowAtr retrictPostAllowFutureFlg;

	private AppDisplayAtr displayPrePostFlg;

	private AppDisplayAtr displaySearchTimeFlg;

	private RetrictDay displayInitDayFlg;

	public ApplicationSettingItem(String companyID, ApplicationType appType, AppDisplayAtr prePostInitFlg,
			CanAtr prePostCanChangeFlg, AppDisplayAtr typicalReasonDisplayFlg, CanAtr sendMailWhenApprovalFlg,
			CanAtr sendMailWhenRegisterFlg, CanAtr displayReasonFlg, VacationAppType vacationAppType,
			CanAtr appActLockFlg, CanAtr appEndWorkFlg, CanAtr appActConfirmFlg, CanAtr appOvertimeNightFlg,
			CanAtr appActMonthConfirmFlg, RequiredFlg requiredAppReasonFlg, CheckMethod retrictPreMethodFlg,
			UseAtr retrictPreUseFlg, RetrictDay retrictPreDay, RetrictPreTimeDay retrictPreTimeDay,
			PossibleAtr rertricPreCanAceeptFlg, AllowAtr retrictPostAllowFutureFlg, AppDisplayAtr displayPrePostFlg,
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
		this.requiredAppReasonFlg = requiredAppReasonFlg;
		this.retrictPreMethodFlg = retrictPreMethodFlg;
		this.retrictPreUseFlg = retrictPreUseFlg;
		this.retrictPreDay = retrictPreDay;
		this.retrictPreTimeDay = retrictPreTimeDay;
		this.rertricPreCanAceeptFlg = rertricPreCanAceeptFlg;
		this.retrictPostAllowFutureFlg = retrictPostAllowFutureFlg;
		this.displayPrePostFlg = displayPrePostFlg;
		this.displaySearchTimeFlg = displaySearchTimeFlg;
		this.displayInitDayFlg = displayInitDayFlg;
	}
	
	
}
