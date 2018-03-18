package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;
/**
 * 予定勤種・就時の反映
 * @author do_dt
 *
 */
public interface AfterWorkTimeTypeReflect {
	/**
	 * 予定勤種・就時の反映
	 * @param para
	 * @return
	 */
	public boolean workTimeAndTypeScheReflect(GobackReflectParameter para);
	/**
	 * 予定勤務種類による勤種・就時を反映できるかチェックする
	 * @param para
	 * @return
	 */
	public boolean checkReflectWorkTimeType(GobackReflectParameter para);
}
