package nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.application.common.PossibleAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RetrictDay;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RetrictPreTimeDay;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

@Value
public class AppTypeDiscreteSetting {
	private String companyID;

	private ApplicationType appType;

	private AppDisplayAtr prePostInitFlg;

	private AppCanAtr prePostCanChangeFlg;

	private AppDisplayAtr typicalReasonDisplayFlg;

	private AppCanAtr sendMailWhenApprovalFlg;

	private AppCanAtr sendMailWhenRegisterFlg;

	private AppDisplayAtr displayReasonFlg;

	private CheckMethod retrictPreMethodFlg;

	private UseAtr retrictPreUseFlg;

	private RetrictDay retrictPreDay;

	private RetrictPreTimeDay retrictPreTimeDay;

	private PossibleAtr retrictPreCanAceeptFlg;

	private AllowAtr retrictPostAllowFutureFlg;

	public AppTypeDiscreteSetting(String companyID, ApplicationType appType, AppDisplayAtr prePostInitFlg,
			AppCanAtr prePostCanChangeFlg, AppDisplayAtr typicalReasonDisplayFlg, AppCanAtr sendMailWhenApprovalFlg,
			AppCanAtr sendMailWhenRegisterFlg, AppDisplayAtr displayReasonFlg, CheckMethod retrictPreMethodFlg,
			UseAtr retrictPreUseFlg, RetrictDay retrictPreDay, RetrictPreTimeDay retrictPreTimeDay,
			PossibleAtr retrictPreCanAceeptFlg, AllowAtr retrictPostAllowFutureFlg) {
		super();
		this.companyID = companyID;
		this.appType = appType;
		this.prePostInitFlg = prePostInitFlg;
		this.prePostCanChangeFlg = prePostCanChangeFlg;
		this.typicalReasonDisplayFlg = typicalReasonDisplayFlg;
		this.sendMailWhenApprovalFlg = sendMailWhenApprovalFlg;
		this.sendMailWhenRegisterFlg = sendMailWhenRegisterFlg;
		this.displayReasonFlg = displayReasonFlg;
		this.retrictPreMethodFlg = retrictPreMethodFlg;
		this.retrictPreUseFlg = retrictPreUseFlg;
		this.retrictPreDay = retrictPreDay;
		this.retrictPreTimeDay = retrictPreTimeDay;
		this.retrictPreCanAceeptFlg = retrictPreCanAceeptFlg;
		this.retrictPostAllowFutureFlg = retrictPostAllowFutureFlg;
	}

}
