package nts.uk.ctx.pr.formula.dom.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum RoundAtr {
	// 0:切り上げ
	ROUND_UP(0),
	// 1:切り捨て
	TRUNCATION(1),
	// 2:一捨二入
	DOWN1_UP2(2),
	// 3:二捨三入
	DOWN2_UP3(3),
	// 4:三捨四入
	DOWN3_UP4(4),
	// 5:四捨五入
	DOWN4_UP5(5),
	// 6:五捨六入
	DOWN5_UP6(6),
	// 7:六捨七入
	DOWN6_UP7(7),
	// 8:七捨八入
	DOWN7_UP8(8),
	// 9:八捨九入
	DOWN8_UP9(9);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "切り上げ";
			break;
		case 1:
			name = "切り捨て";
			break;
		case 2:
			name = "一捨二入";
			break;
		case 3:
			name = "二捨三入";
			break;
		case 4:
			name = "三捨四入";
			break;
		case 5:
			name = "四捨五入";
			break;
		case 6:
			name = "五捨六入";
			break;
		case 7:
			name = "六捨七入";
			break;
		case 8:
			name = "七捨八入";
			break;
		case 9:
			name = "八捨九入";
			break;
		default:
			name = "切り上げ";
			break;
		}

		return name;
	}
}
