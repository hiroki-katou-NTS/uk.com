package nts.uk.ctx.pr.proto.dom.layoutmasterdetail;

/**
 * 
 * 計算方法
 *
 */
public enum CalculationMethod {
	// 0:手入力
	MANUAL_ENTRY,
	// 1:個人情報
	PERSONAL_INFORMATION,
	// 2:計算式
	FORMULA,
	// 3:賃金テーブル
	WAGE_TABLE,
	// 4: 共通金額
	COMMON_AMOUNT_MONEY,
	// 5:支給相殺
	PAYMENT_CANCELED
}
