package nts.uk.ctx.at.request.app.find.setting.request.application.apptypediscretesetting;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.application.common.PossibleAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RetrictDay;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RetrictPreTimeDay;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;

@AllArgsConstructor
public class AppTypeDiscreteSettingDto {
	public String companyID;
	public Integer appType;
	public Integer prePostInitFlg;
	public Integer prePostCanChangeFlg;
	public Integer typicalReasonDisplayFlg;
	public Integer sendMailWhenApprovalFlg;
	public Integer sendMailWhenRegisterFlg;
	public Integer displayReasonFlg;
	public Integer retrictPreMethodFlg;
	public Integer retrictPreUseFlg;
	public Integer retrictPreDay;
	public Integer retrictPreTimeDay;
	public Integer retrictPreCanAceeptFlg;
	public Integer retrictPostAllowFutureFlg;

	public static AppTypeDiscreteSettingDto convertToDto(AppTypeDiscreteSetting domain) {
		return new AppTypeDiscreteSettingDto(
				domain.getCompanyID(),
				domain.getAppType().value,
				domain.getPrePostInitFlg().value,
				domain.getPrePostCanChangeFlg().value, 
				domain.getTypicalReasonDisplayFlg().value,
				domain.getSendMailWhenApprovalFlg().value,
				domain.getSendMailWhenRegisterFlg().value,
				domain.getDisplayReasonFlg().value,
				domain.getRetrictPreMethodFlg().value,
				domain.getRetrictPreUseFlg().value,
				domain.getRetrictPreDay().value,
				domain.getRetrictPreTimeDay().v(),
				domain.getRetrictPreCanAceeptFlg().value,
				domain.getRetrictPostAllowFutureFlg().value);
	}
	
	public AppTypeDiscreteSetting toDomain() {
		return new AppTypeDiscreteSetting(
				companyID, 
				EnumAdaptor.valueOf(appType, ApplicationType_Old.class), 
				EnumAdaptor.valueOf(prePostInitFlg, InitValueAtr.class), 
				EnumAdaptor.valueOf(prePostCanChangeFlg, AppCanAtr.class), 
				EnumAdaptor.valueOf(typicalReasonDisplayFlg, DisplayAtr.class), 
				EnumAdaptor.valueOf(sendMailWhenApprovalFlg, AppCanAtr.class), 
				EnumAdaptor.valueOf(sendMailWhenRegisterFlg, AppCanAtr.class), 
				EnumAdaptor.valueOf(displayReasonFlg, AppDisplayAtr.class), 
				EnumAdaptor.valueOf(retrictPreMethodFlg, CheckMethod.class), 
				EnumAdaptor.valueOf(retrictPreUseFlg, UseAtr.class), 
				EnumAdaptor.valueOf(retrictPreDay, RetrictDay.class), 
				new RetrictPreTimeDay(retrictPreTimeDay), 
				EnumAdaptor.valueOf(retrictPreCanAceeptFlg, PossibleAtr.class), 
				EnumAdaptor.valueOf(retrictPostAllowFutureFlg, AllowAtr.class));
	}
}
