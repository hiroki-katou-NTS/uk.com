package nts.uk.ctx.at.function.dom.adapter.workrecord.approvalmanagement;

import lombok.Value;

/**
 * 
 * @author thuongtv
 *
 */
@Value
public class ApprovalProcessImport {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 職位ID
	 */
	private String jobTitleId;

	/**
	 * 日の承認者確認を利用する
	 */
	private int useDailyBossChk;

	/**
	 * 月の承認者確認を利用する
	 */
	private int useMonthBossChk;

	/**
	 * エラーがある場合の上司確認
	 */
	private Integer supervisorConfirmError;
}
