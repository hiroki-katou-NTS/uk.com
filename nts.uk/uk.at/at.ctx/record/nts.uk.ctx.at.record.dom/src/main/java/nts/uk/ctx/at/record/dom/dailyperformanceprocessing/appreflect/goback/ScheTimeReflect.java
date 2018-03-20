package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;
/**
 * 事前申請の処理: 予定時刻の反映
 * 時刻の反映
 * @author do_dt
 *
 */
public interface ScheTimeReflect {
	/**
	 * 予定時刻の反映
	 * @param para
	 * @param timeTypeScheReflect: 予定勤種・就時の反映
	 * @return
	 */
	public void reflectScheTime(GobackReflectParameter para, boolean timeTypeScheReflect);
	/**
	 * 反映する時刻を求める
	 * @param para
	 * @param timeTypeScheReflect
	 * @return
	 */
	public TimeOfDayReflectOutput getTimeOfDayReflect(GobackReflectParameter para, boolean timeTypeScheReflect, ApplyTimeAtr applyTimeAtr);
	/**
	 * 時刻の反映
	 * @param para
	 */
	public void reflectTime(GobackReflectParameter para, boolean workTypeTimeReflect);
	/**
	 * 1.出勤時刻を反映できるか
	 * 2.退勤時刻を反映できるか
	 * @param para
	 */
	public boolean checkAttendenceReflect(GobackReflectParameter para, Integer frameNo, boolean isPre);
	/**
	 * ジャスト遅刻により時刻を編集する
	 * ジャスト早退により時刻を編集する
	 * @param workTimeCode
	 * @param timeData
	 * @param frameNo
	 * @return
	 */
	public Integer justTimeLateLeave(String workTimeCode, Integer timeData, Integer frameNo, boolean isPre);
}
