package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StampCardEditMethod {

	// 前ゼロ
	PreviousZero(1),

	// 後ゼロ
	AfterZero(2),

	// 前スペース
	PreviousSpace(3),

	// 後スペース
	AfterSpace(4);

	public final int value;

	/**
	 * [1] 打刻カード番号を編集する
	 * 
	 * @param 桁数
	 *            number
	 * @param 編集前番号
	 *            beforeEditNumber
	 * 
	 * @return 編集後番号 result
	 */
	public String editCardNumber(String number, String beforeEditNumber) {
		// $編集内容 = ""
		String result = "";

		// if this == 前ゼロ
		if (this == StampCardEditMethod.PreviousZero) {
			result = "%0" + number + "s";
		}
		// if this == 後ゼロ

		if (this == StampCardEditMethod.AfterZero) {
			result = "%-0" + number + "s";
		}

		// if this == 前スペース
		if (this == StampCardEditMethod.PreviousSpace) {
			result = "%" + number + "s";
		}

		// if this == 前ゼロ
		if (this == StampCardEditMethod.AfterSpace) {
			result = "%-" + number + "s";
		}

		return String.format(result, beforeEditNumber);

	}

}
