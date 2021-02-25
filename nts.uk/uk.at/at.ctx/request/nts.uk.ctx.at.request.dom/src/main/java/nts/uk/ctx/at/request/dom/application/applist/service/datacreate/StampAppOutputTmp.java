package nts.uk.ctx.at.request.dom.application.applist.service.datacreate;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.stamp.StampFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4.打刻申請データを作成.打刻申請出力用Tmp
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class StampAppOutputTmp {
	
	/**
	 * 時刻時間項目
	 */
	private int timeItem;
	
	/**
	 * 取消
	 */
	private boolean cancel;
	
	/**
	 * 打刻分類区分
	 */
	private int stampAtr;
	
	/**
	 * 打刻枠No
	 */
	private StampFrameNo stampFrameNo;
	
	/**
	 * 開始時刻
	 */
	private Optional<TimeWithDayAttr> opStartTime;
	
	/**
	 * 外出理由
	 */
	private Optional<GoingOutReason> opGoOutReasonAtr;
	
	/**
	 * 項目名
	 */
	private Optional<String> opItemName;
	
	/**
	 * 終了時刻
	 */
	private Optional<TimeWithDayAttr> opEndTime;
	
}
