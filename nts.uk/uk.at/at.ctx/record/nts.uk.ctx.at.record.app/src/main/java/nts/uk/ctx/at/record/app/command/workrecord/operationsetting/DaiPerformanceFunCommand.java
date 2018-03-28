package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class DaiPerformanceFunCommand {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コメント
	 */
	private String comment;

	/**
	 * 1ヵ月の確認・承認が完了した場合、メッセージを表示する
	 */
	private int monthChkMsgAtr;

	/**
	 * 36協定情報を表示する
	 */
	private int disp36Atr;

	/**
	 * クリアした内容は手修正にする
	 */
	private int clearManuAtr;

	/**
	 * フレックス勤務者のフレックス不足情報を表示する
	 */
	private int flexDispAtr;

	/**
	 * 休出計算区分を変更する場合、休出深夜計算区分を変更する
	 */
	private int breakCalcUpdAtr;

	/**
	 * 休憩時刻を自動で設定する
	 */
	private int breakTimeAutoAtr;

	/**
	 * 休日の場合、出勤/退勤時刻をクリアにする
	 */
	private int breakClrTimeAtr;

	/**
	 * 出勤/退勤時刻を自動で設定する
	 */
	private int autoSetTimeAtr;

	/**
	 * 早出計算区分を変更する場合、早出残業深夜計算区分を変更する
	 */
	private int ealyCalcUpdAtr;

	/**
	 * 残業計算区分を変更する場合、残業深夜区分を変更する
	 */
	private int overtimeCalcUpdAtr;

	/**
	 * 法定内残業計算区分を変更する場合、法定内深夜残業計算区分を変更する
	 */
	private int lawOverCalcUpdAtr;

	/**
	 * 自動で設定した内容は手修正にする
	 */
	private int manualFixAutoSetAtr;
}
