package nts.uk.ctx.at.request.app.find.request.gobackdirectlycommon;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class GoBackDirectlyCommonSettingDto {
	private String companyID;

	private String appID;

	private int workChangeFlg;

	private int workChangeTimeAtr;

	private int performanceDisplayAtr;

	private int contraditionCheckAtr;

	private int workType;

	private int lateLeaveEarlySetAtr;

	private String commentContent1;

	private int commentFontWeight1;

	private String commentFontColor1;

	private String commentContent2;

	private int commentFontWeight2;

	private String commentFontColor2;

}
