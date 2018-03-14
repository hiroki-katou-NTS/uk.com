package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;
/**
 * 残業申請: 事前申請処理
 * @author do_dt
 *
 */
public interface PriorOvertimeReflectService {
	/**
	 * 事前申請の処理
	 * @param param
	 * @return
	 */
	public OvertimeReflectOutput overtimeReflect(PreOvertimeParameter param);
}
