package nts.uk.ctx.at.request.app.find.setting.request.application.apptypediscretesetting;

import lombok.AllArgsConstructor;

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

//	public static AppTypeDiscreteSettingDto convertToDto(AppTypeDiscreteSetting domain) {
//		return new AppTypeDiscreteSettingDto(
//				domain.getCompanyID(),
//				domain.getAppType().value,
//				domain.getPrePostInitFlg().value,
//				domain.getPrePostCanChangeFlg().value, 
//				domain.getTypicalReasonDisplayFlg().value,
//				domain.getSendMailWhenApprovalFlg().value,
//				domain.getSendMailWhenRegisterFlg().value,
//				domain.getDisplayReasonFlg().value,
//				domain.getRetrictPreMethodFlg().value,
//				domain.getRetrictPreUseFlg().value,
//				domain.getRetrictPreDay().value,
//				domain.getRetrictPreTimeDay().v(),
//				domain.getRetrictPreCanAceeptFlg().value,
//				domain.getRetrictPostAllowFutureFlg().value);
//	}
//	
//	public AppTypeDiscreteSetting toDomain() {
//		return new AppTypeDiscreteSetting(
//				companyID, 
//				EnumAdaptor.valueOf(appType, ApplicationType.class), 
//				EnumAdaptor.valueOf(prePostInitFlg, InitValueAtr.class), 
//				EnumAdaptor.valueOf(prePostCanChangeFlg, AppCanAtr.class), 
//				EnumAdaptor.valueOf(typicalReasonDisplayFlg, DisplayAtr.class), 
//				EnumAdaptor.valueOf(sendMailWhenApprovalFlg, AppCanAtr.class), 
//				EnumAdaptor.valueOf(sendMailWhenRegisterFlg, AppCanAtr.class), 
//				EnumAdaptor.valueOf(displayReasonFlg, AppDisplayAtr.class), 
//				EnumAdaptor.valueOf(retrictPreMethodFlg, CheckMethod.class), 
//				EnumAdaptor.valueOf(retrictPreUseFlg, UseAtr.class), 
//				EnumAdaptor.valueOf(retrictPreDay, RetrictDay.class), 
//				new RetrictPreTimeDay(retrictPreTimeDay), 
//				EnumAdaptor.valueOf(retrictPreCanAceeptFlg, PossibleAtr.class), 
//				EnumAdaptor.valueOf(retrictPostAllowFutureFlg, AllowAtr.class));
//	}
}
