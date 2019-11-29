package nts.uk.ctx.at.record.dom.workrecord.closurestatus.export;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;

/**
 * 次回年休付与を計算する開始日を取得する
 * @author shuichi_ishida
 */
public interface GetCalcStartForNextLeaveGrant {

	/**
	 * 次回年休付与を計算する開始日を取得する
	 * @param employeeId 社員ID
	 * @return 年月日
	 */
	GeneralDate algorithm(String employeeId);
	/**
	 * dùng cho màn cps003
	 * 次回年休付与を計算する開始日を取得する
	 * @param sid list 社員ID list
	 * @return  Map<sid, 年月日>
	 */
	Map<String, GeneralDate> algorithm(List<String> sids);
}
