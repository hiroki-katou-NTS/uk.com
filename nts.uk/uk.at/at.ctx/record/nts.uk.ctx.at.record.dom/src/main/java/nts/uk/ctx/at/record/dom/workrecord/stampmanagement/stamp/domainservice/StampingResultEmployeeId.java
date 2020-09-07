package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import lombok.AllArgsConstructor;

/**
 * 打刻結果（社員ID込み）
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻.ICカードから打刻を入力する.打刻結果（社員ID込み）
 * @author chungnt
 *
 */

@AllArgsConstructor
public class StampingResultEmployeeId {

	/**
	 *打刻入力結果
	 */
	public final TimeStampInputResult inputResult;
	
	/**
	 * 社員ID
	 */
	public final String employeeId;
}
