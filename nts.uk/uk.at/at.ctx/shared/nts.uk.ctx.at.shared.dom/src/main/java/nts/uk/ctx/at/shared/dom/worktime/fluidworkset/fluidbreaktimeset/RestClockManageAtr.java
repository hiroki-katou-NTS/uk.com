package nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset;

/**
 * 休憩打刻の時刻管理設定区分
 * @author keisuke_hoshina
 *
 */
public enum RestClockManageAtr {
	isClockManage,
	notClockManage
	
	;
	/**
	 * 時刻管理するか判定する
	 * @return　時刻管理をする
	 */
	public boolean isClockManage() {
		return isClockManage.equals(this);
	}
	
	/**
	 * 時刻管理しないか判定する
	 * @return　時刻管理をしない
	 */
	public boolean isNotClockManage() {
		return notClockManage.equals(this);
	}
}
