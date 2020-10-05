package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
/**
 * 打刻申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class StampRequestSetting_Old {
	
	private String companyID;
	
	/**
	 * 上部コメント
	 */
	private AppCommentSetting topComment;
	
	/**
	 * 下部コメント
	 */
	private AppCommentSetting bottomComment;
	
	/**
	 * 実績を表示する
	 */
	private DisplayAtr resultDisp;
	
	/**
	 * 応援枠の表示件数
	 */
	private SupportFrameDispNumber supFrameDispNO;
	
	/**
	 * 打刻場所を表示する
	 */
	private DisplayAtr stampPlaceDisp;

	/**
	 * 打刻区分の表示制御
	 */
	private StampDisplayControl stampDisplayControl;
	
	/**
	 * 外出種類の表示制御
	 */
	private GoOutTypeDisplayControl goOutTypeDisplayControl;
	
	public static StampRequestSetting_Old createFromJavaType(String companyId, String commentTop, String fontColorTop, Boolean fontWeightTop, 
			String commentBottom, String fontColorBottom, Boolean fontWeightBottom, Integer resultDisp, Integer supFrameDispNO,
			Integer stampPlaceDisp, Integer stampAtrWorkDisp, Integer stampAtrGoOutDisp, Integer stampAtrCareDisp,
			Integer stampAtrSupDisp, Integer stampAtrChildCareDisp, Integer stampGoOutAtrPrivateDisp, Integer stampGoOutAtrPublicDisp,
			Integer stampGoOutAtrCompensationDisp, Integer stampGoOutAtrUnionDisp){
		return new StampRequestSetting_Old(companyId, new AppCommentSetting(new Comment(commentTop), fontColorTop, fontWeightTop), 
										new AppCommentSetting(new Comment(commentBottom), fontColorBottom, fontWeightBottom), 
										EnumAdaptor.valueOf(resultDisp, DisplayAtr.class), 
										new SupportFrameDispNumber(supFrameDispNO) , 
										EnumAdaptor.valueOf(stampPlaceDisp, DisplayAtr.class), 
										new StampDisplayControl(EnumAdaptor.valueOf(stampAtrWorkDisp, DisplayAtr.class), 
												EnumAdaptor.valueOf(stampAtrGoOutDisp, DisplayAtr.class), 
												EnumAdaptor.valueOf(stampAtrCareDisp, DisplayAtr.class), 
												EnumAdaptor.valueOf(stampAtrSupDisp, DisplayAtr.class),
												EnumAdaptor.valueOf(stampAtrChildCareDisp, DisplayAtr.class)),
										new GoOutTypeDisplayControl(EnumAdaptor.valueOf(stampGoOutAtrPrivateDisp, DisplayAtr.class),
												EnumAdaptor.valueOf(stampGoOutAtrPublicDisp, DisplayAtr.class),
												EnumAdaptor.valueOf(stampGoOutAtrCompensationDisp, DisplayAtr.class),
												EnumAdaptor.valueOf(stampGoOutAtrUnionDisp, DisplayAtr.class)));
		 
	}
}
