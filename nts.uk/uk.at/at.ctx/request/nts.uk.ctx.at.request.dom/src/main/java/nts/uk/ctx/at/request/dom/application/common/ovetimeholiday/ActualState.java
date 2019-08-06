package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

/**
 * @author ductm
 * 実績状態
 */
public enum ActualState {
	/**
	 * 実績なし
	 */
	ACTUALNOTEXIST(0),
	/**
	 * 打刻漏れ
	 */
	STAMPLEAK(1),
	/**
	 * 退勤打刻補正
	 */
	LEAVESTAMPCORRECTION(2),
	/**
	 * システム時刻打刻
	 */
	CURRENTTIMESTAMP(3),
	/**
	 * 就業時間帯変更
	 */
	WORKTIMECHANGE(4),
	/**
	 * 実績補正なし
	 */
	ACTUALCORRECTIONNOTEXIST(5);
	
	public final int value;
	
	ActualState(int value){
		this.value = value;
	}
	

}