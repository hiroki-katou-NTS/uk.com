package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.item;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.Deadline;
import nts.uk.ctx.at.request.dom.setting.request.application.DeadlineCriteria;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.CheckAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.CommentContent;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.CommentFontColor;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.FontWeightFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.GoBackWorkType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.WorkChangeFlg;

@Value
public class GoBackDirectlyCommonSettingItem  {
	private String companyID;

	private String appID;

	private WorkChangeFlg workChangeFlg;

	private AppDisplayAtr performanceDisplayAtr;

	private CheckAtr contraditionCheckAtr;

	private GoBackWorkType goBackWorkType;

	private CheckAtr lateLeaveEarlySettingAtr;

	private CommentContent commentContent1;

	private FontWeightFlg commentFontWeight1;

	private CommentFontColor commentFontColor1;

	private CommentContent commentContent2;

	private FontWeightFlg commentFontWeight2;

	private CommentFontColor commentFontColor2;

	public GoBackDirectlyCommonSettingItem(String companyID, String appID, WorkChangeFlg workChangeFlg,
			AppDisplayAtr performanceDisplayAtr, CheckAtr contraditionCheckAtr, GoBackWorkType goBackWorkType,
			CheckAtr lateLeaveEarlySettingAtr, CommentContent commentContent1, FontWeightFlg commentFontWeight1,
			CommentFontColor commentFontColor1, CommentContent commentContent2, FontWeightFlg commentFontWeight2,
			CommentFontColor commentFontColor2) {
		super();
		this.companyID = companyID;
		this.appID = appID;
		this.workChangeFlg = workChangeFlg;
		this.performanceDisplayAtr = performanceDisplayAtr;
		this.contraditionCheckAtr = contraditionCheckAtr;
		this.goBackWorkType = goBackWorkType;
		this.lateLeaveEarlySettingAtr = lateLeaveEarlySettingAtr;
		this.commentContent1 = commentContent1;
		this.commentFontWeight1 = commentFontWeight1;
		this.commentFontColor1 = commentFontColor1;
		this.commentContent2 = commentContent2;
		this.commentFontWeight2 = commentFontWeight2;
		this.commentFontColor2 = commentFontColor2;
	}

}
