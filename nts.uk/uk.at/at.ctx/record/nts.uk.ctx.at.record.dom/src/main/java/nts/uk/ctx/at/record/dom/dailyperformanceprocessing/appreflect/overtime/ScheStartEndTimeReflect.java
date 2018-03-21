package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;
/**
 * 予定開始終了時刻の反映(事前事後共通部分)
 * @author do_dt
 *
 */
public interface ScheStartEndTimeReflect {
	/**
	 * 予定開始終了時刻の反映(事前事後共通部分)
	 * @param para
	 * @param timeTypeData
	 * @return
	 */
	public ScheStartEndTimeReflectOutput reflectScheStartEndTime(PreOvertimeParameter para, WorkTimeTypeOutput timeTypeData);
	/**
	 * 反映する開始終了時刻を求める
	 * @param para
	 * @param timeTypeData
	 * @return
	 */
	public ScheStartEndTimeReflectOutput findStartEndTime(PreOvertimeParameter para, WorkTimeTypeOutput timeTypeData);
}
