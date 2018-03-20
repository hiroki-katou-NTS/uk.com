package nts.uk.ctx.at.shared.dom.worktype.service;
/**
 * 勤務種類が休出振出かの判断
 * @author do_dt
 *
 */
public interface WorkTypeIsClosedService {
	/**
	 * 勤務種類が休出振出かの判断
	 * @param workTimeCode
	 * @return
	 */
	public boolean checkWorkTypeIsClosed(String workTypeCode);
}
