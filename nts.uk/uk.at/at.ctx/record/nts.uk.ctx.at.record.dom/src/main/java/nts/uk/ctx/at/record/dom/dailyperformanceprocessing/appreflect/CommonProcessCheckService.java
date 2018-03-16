package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;
/**
 * 反映状況によるチェック
 * @author do_dt
 *
 */
public interface CommonProcessCheckService {
	/**
	 * 反映状況によるチェック
	 * @param para
	 * @return false: 反映しない, true: 反映する
	 */
	public boolean commonProcessCheck(CommonCheckParameter para);

}
