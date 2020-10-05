package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.AllArgsConstructor;

/**
 * 結果区分
 * @author khai.dh
 */
@AllArgsConstructor
public enum ResultType {

	// エラーなし
	NO_ERROR(0),

	//	承認者が未設定
	APPROVER_NOT_SET(1),

	// 1ヶ月の上限時間を超過している
	MONTHLY_LIMIT_EXCEEDED(2),

	// 1年間の上限時間を超過している
	YEARLY_LIMIT_EXCEEDED(3);

	public final int value;
}
