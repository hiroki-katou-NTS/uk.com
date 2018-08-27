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
	private boolean monthChkMsgAtr;

	/**
	 * 36協定情報を表示する
	 */
	private boolean disp36Atr;

	/**
	 * クリアした内容は手修正にする
	 */
	private boolean clearManuAtr;

	/**
	 * フレックス勤務者のフレックス不足情報を表示する
	 */
	private boolean flexDispAtr;

	/**
	 * 休出計算区分を変更する場合、休出深夜計算区分を変更する
	 */
	private boolean breakCalcUpdAtr;

	/**
	 * 休憩時刻を自動で設定する
	 */
	private boolean breakTimeAutoAtr;

	/**
	 * 休日の場合、出勤/退勤時刻をクリアにする
	 */
	private boolean breakClrTimeAtr;

	/**
	 * 出勤/退勤時刻を自動で設定する
	 */
	private boolean autoSetTimeAtr;

	/**
	 * 早出計算区分を変更する場合、早出残業深夜計算区分を変更する
	 */
	private boolean ealyCalcUpdAtr;

	/**
	 * 残業計算区分を変更する場合、残業深夜区分を変更する
	 */
	private boolean overtimeCalcUpdAtr;

	/**
	 * 法定内残業計算区分を変更する場合、法定内深夜残業計算区分を変更する
	 */
	private boolean lawOverCalcUpdAtr;

	/**
	 * 自動で設定した内容は手修正にする
	 */
	private boolean manualFixAutoSetAtr;
	
    /**
     * 抽出時にエラーがある場合はエラー参照ダイアログを表示する
     * 
     */
    private boolean checkErrRefDisp;
}
