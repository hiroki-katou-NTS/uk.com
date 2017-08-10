package nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.item;

import lombok.Value;
import nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive.CheckAtr;
import nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive.CommentContent;
import nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive.CommentFontColor;
import nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive.FontWeight;
import nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive.WorkChangeFlg;
import nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive.WorkType;

@Value
public class GoBackDirectlyCommonSettingItem {
	private String companyID;

	private String appID;

	private WorkChangeFlg workChangeFlg;

	private DisplayAtr performanceDisplayAtr;

	private CheckAtr contraditionCheckAtr;

	private WorkType workType;

	private CheckAtr lateLeaveEarlySettingAtr;

	private CommentContent commentContent1;

	private FontWeight commentFontWeight1;

	private CommentFontColor commentFontColor1;

	private CommentContent commentContent2;

	private FontWeight commentFontWeight2;

	private CommentFontColor commentFontColor2;

	public GoBackDirectlyCommonSettingItem(String companyID, String appID, WorkChangeFlg workChangeFlg,
			DisplayAtr performanceDisplayAtr, CheckAtr contraditionCheckAtr, WorkType workType,
			CheckAtr lateLeaveEarlySettingAtr, CommentContent commentContent1, FontWeight commentFontWeight1,
			CommentFontColor commentFontColor1, CommentContent commentContent2, FontWeight commentFontWeight2,
			CommentFontColor commentFontColor2) {
		super();
		this.companyID = companyID;
		this.appID = appID;
		this.workChangeFlg = workChangeFlg;
		this.performanceDisplayAtr = performanceDisplayAtr;
		this.contraditionCheckAtr = contraditionCheckAtr;
		this.workType = workType;
		this.lateLeaveEarlySettingAtr = lateLeaveEarlySettingAtr;
		this.commentContent1 = commentContent1;
		this.commentFontWeight1 = commentFontWeight1;
		this.commentFontColor1 = commentFontColor1;
		this.commentContent2 = commentContent2;
		this.commentFontWeight2 = commentFontWeight2;
		this.commentFontColor2 = commentFontColor2;
	}

}
