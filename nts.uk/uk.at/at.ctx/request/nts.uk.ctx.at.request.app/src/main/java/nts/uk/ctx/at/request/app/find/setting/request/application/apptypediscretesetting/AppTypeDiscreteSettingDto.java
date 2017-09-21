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
}
