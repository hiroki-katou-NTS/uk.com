package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
/**
 * 打刻申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class StampRequestSetting {
	
	private String companyID;
	
	/**
	 * 上部コメント
	 * 申請コメント設定.コメント
	 */
	private Comment topComment;
	
	/**
	 * 上部コメント
	 * 申請コメント設定.文字色
	 */
	private String topCommentFontColor;
	
	/**
	 * 上部コメント
	 * 申請コメント設定.太字
	 */
	private Boolean topCommentFontWeight;
	
	/**
	 * 下部コメント
	 * 申請コメント設定.コメントト
	 */
	private Comment bottomComment;
	
	/**
	 * 下部コメント
	 * 申請コメント設定.文字色
	 */
	private String bottomCommentFontColor;
	
	/**
	 * 下部コメント
	 * 申請コメント設定.太字
	 */
	private Boolean bottomCommentFontWeight;
	
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
	 * 打刻区分の表示制御.出勤／退勤
	 */
	private DisplayAtr stampAtrWorkDisp;
	
	/**
	 * 打刻区分の表示制御.外出/戻り
	 */
	private DisplayAtr stampAtrGoOutDisp;
	
	/**
	 * 打刻区分の表示制御.介護外出/介護戻り
	 */
	private DisplayAtr stampAtrCareDisp;
	
	/**
	 * 打刻区分の表示制御.応援入/応援出
	 */
	private DisplayAtr stampAtrSupDisp;
	
	/**
	 * 外出種類の表示制御.私用
	 */
	private DisplayAtr stampGoOutAtrPrivateDisp;
	
	/**
	 * 外出種類の表示制御.公用
	 */
	private DisplayAtr stampGoOutAtrPublicDisp;
	
	/**
	 * 外出種類の表示制御.有償
	 */
	private DisplayAtr stampGoOutAtrCompensationDisp;
	
	/**
	 * 外出種類の表示制御.組合
	 */
	private DisplayAtr stampGoOutAtrUnionDisp;
	
}
