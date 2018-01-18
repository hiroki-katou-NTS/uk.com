package nts.uk.ctx.at.request.dom.setting.stamp;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * 打刻申請設定
 *
 */
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class StampRequestSetting extends AggregateRoot {
	
	private String companyID;
	
	// 上部コメント
	// 申請コメント設定.コメント
	private String topComment;
	
	// 上部コメント
	// 申請コメント設定.文字色
	private String topCommentFontColor;
	
	// 上部コメント
	// 申請コメント設定.太字
	private String topCommentFontWeight;
	
	// 下部コメント
	// 申請コメント設定.コメント
	private String bottomComment;
	
	// 下部コメント
	// 申請コメント設定.文字色
	private String bottomCommentFontColor;
	
	// 下部コメント
	// 申請コメント設定.太字
	private String bottomCommentFontWeight;
	
	// 実績を表示する
	private int resultDisp;
	
	// 応援枠の表示件数
	private int supFrameDispNO;
	
	// 打刻場所を表示する
	private int stampPlaceDisp;

	// 打刻区分の表示制御.出勤／退勤
	private int stampAtrWorkDisp;
	
	// 打刻区分の表示制御.外出/戻り
	private int stampAtrGoOutDisp;
	
	// 打刻区分の表示制御.介護外出/介護戻り
	private int stampAtrCareDisp;
	
	// 打刻区分の表示制御.応援入/応援出
	private int stampAtrSupDisp;
	
	// 外出種類の表示制御.私用
	private int stampGoOutAtrPrivateDisp;
	
	// 外出種類の表示制御.公用
	private int stampGoOutAtrPublicDisp;
	
	// 外出種類の表示制御.有償
	private int stampGoOutAtrCompensationDisp;
	
	// 外出種類の表示制御.組合
	private int stampGoOutAtrUnionDisp;

	public StampRequestSetting(String companyID, String topComment, String topCommentFontColor,
			String topCommentFontWeight, String bottomComment, String bottomCommentFontColor,
			String bottomCommentFontWeight, int resultDisp, int supFrameDispNO, int stampPlaceDisp,
			int stampAtrWorkDisp, int stampAtrGoOutDisp, int stampAtrCareDisp, int stampAtrSupDisp,
			int stampGoOutAtrPrivateDisp, int stampGoOutAtrPublicDisp, int stampGoOutAtrCompensationDisp,
			int stampGoOutAtrUnionDisp) {
		super();
		this.companyID = companyID;
		this.topComment = topComment;
		this.topCommentFontColor = topCommentFontColor;
		this.topCommentFontWeight = topCommentFontWeight;
		this.bottomComment = bottomComment;
		this.bottomCommentFontColor = bottomCommentFontColor;
		this.bottomCommentFontWeight = bottomCommentFontWeight;
		this.resultDisp = resultDisp;
		this.supFrameDispNO = supFrameDispNO;
		this.stampPlaceDisp = stampPlaceDisp;
		this.stampAtrWorkDisp = stampAtrWorkDisp;
		this.stampAtrGoOutDisp = stampAtrGoOutDisp;
		this.stampAtrCareDisp = stampAtrCareDisp;
		this.stampAtrSupDisp = stampAtrSupDisp;
		this.stampGoOutAtrPrivateDisp = stampGoOutAtrPrivateDisp;
		this.stampGoOutAtrPublicDisp = stampGoOutAtrPublicDisp;
		this.stampGoOutAtrCompensationDisp = stampGoOutAtrCompensationDisp;
		this.stampGoOutAtrUnionDisp = stampGoOutAtrUnionDisp;
	}
}
