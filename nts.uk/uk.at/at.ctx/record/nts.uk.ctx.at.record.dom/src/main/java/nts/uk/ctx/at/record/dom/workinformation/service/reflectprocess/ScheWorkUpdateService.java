package nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess;

import java.util.List;

/**
 * 反映処理
 * @author do_dt
 *
 */
public interface ScheWorkUpdateService {
	/**
	 * 予定勤種・就時の反映
	 * @param para
	 */
	public void updateWorkTimeType(ReflectParameter para, 
			List<Integer> lstItem,
			boolean scheUpdate);
	/**
	 * 予定時刻の反映
	 * @param data
	 */
	public void updateStartTimeOfReflect(TimeReflectParameter data);
	/**
	 * 開始時刻の反映
	 * @param data
	 */
	public void updateReflectStartEndTime(TimeReflectParameter para);

}
