package nts.uk.ctx.at.shared.dom.worktime.service;
/**
 * 流動勤務かどうかの判断処理
 * @author do_dt
 *
 */
public interface WorkTimeIsFluidWork {
	/**
	 * 流動勤務かどうかの判断処理
	 * @param workTimeCode
	 * @return
	 */
	public boolean checkWorkTimeIsFluidWork(String workTimeCode);

}
