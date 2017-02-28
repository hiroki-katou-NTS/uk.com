package nts.uk.ctx.pr.formula.dom.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum RoundAtr {
	// 0:切り上げ
	ROUNDED(0),
	// 1:切り捨て
	ROUND_UP(1),
	// 2:一捨二入
	TRUNCATION(2),
	// 3:二捨三入
	NO_ROUND(3);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "四捨五入";
			break;
		case 1:
			name = "切り上げ";
			break;
		case 2:
			name = "切り捨て";
			break;
		case 3:
			name = "端数処理なし";
			break;
		default:
			name = "四捨五入";
			break;
		}

		return name;
	}
}
