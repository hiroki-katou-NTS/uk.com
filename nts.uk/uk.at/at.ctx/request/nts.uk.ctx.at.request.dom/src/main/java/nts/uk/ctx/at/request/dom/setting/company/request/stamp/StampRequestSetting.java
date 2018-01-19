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
	
}
