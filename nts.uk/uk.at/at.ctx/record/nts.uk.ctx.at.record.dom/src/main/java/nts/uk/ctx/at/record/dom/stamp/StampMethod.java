package nts.uk.ctx.at.record.dom.stamp;

import lombok.AllArgsConstructor;
/**
 * 打刻方法
 * @author dudt
 *
 */
@AllArgsConstructor
public enum StampMethod {
	// タイムレコーダー
	TIMERECORDER(0, "タイムレコーダー"),
	// Web
	WEB(1, "Web"),
	// ID入力
	IDINPUT(2, "ID入力"),
	// 磁気カード
	MAGNETICCARD(3, "磁気カード"),
	// ICカード
	ICCARD(4, "ICカード"),
	// 指紋
	FINGERPRINT(5, "指紋"),
	// その他
	OTHER(6, "その他");
	public final int value;
	public final String name;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "タイムレコーダー";
			break;
		case 1:
			name = "Web";
			break;
		case 2:
			name = "ID入力";
			break;
		case 3:
			name = "磁気カード";
			break;
		case 4:
			name = "ICカード";
			break;
		case 5:
			name = "指紋";
			break;
		default:
			name = "その他";
			break;
		}
		return name;
	}
}
