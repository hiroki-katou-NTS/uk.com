package nts.uk.ctx.at.record.dom.stamp;

import lombok.AllArgsConstructor;
/**
 * 
 * @author dudt
 *
 */
@AllArgsConstructor
public enum StampAtr {
	// 0:出勤
	ATTENDANCE(0, "出勤"),
	// 1:退勤
	WORKONTIME(1, "退勤"),
	// 2:入門
	INTRODUCTION(2, "入門"),
	// 3:退門
	EXIT(3, "退門"),
	// 4:外出
	GOINGOUT(4, "外出"),
	// 5:戻り
	RETURN(5, "戻り"),
	// 6:解錠
	UNLOCK(6, "解錠"),
	// 7:解錠
	LOCK(7, "解錠"),
	// 8:開始
	START(8, "開始"),
	// 9:終了
	END(9, "終了"),
	// 10:行き
	TOGO(10, "行き"),
	// 11:帰り
	GOBACK(11, "帰り"),
	// 12: PCログオン
	PCLOGON(12, "PCログオン"),
	//13: PCログオフ
	PCLOGOFF(13, "PCログオフ");

	public final int value;
	public final String name;

	public String toName() {
		String name;
		switch (value) {
		case 1:
			name = "退勤";
			break;
		case 2:
			name = "入門";
			break;
		case 3:
			name = "退門";
			break;
		case 4:
			name = "外出";
			break;
		case 5:
			name = "戻り";
			break;
		case 6:
			name = "解錠";
			break;
		case 7:
			name = "施錠";
			break;
		case 8:
			name = "開始";
			break;
		case 9:
			name = "終了";
			break;
		case 10:
			name = "行き";
			break;
		case 11:
			name = "帰り";
			break;
		case 12:
			name = "PCログオン";
			break;
		case 13:
			name = "PCログオフ";
			break;
		default:
			name = "出勤";
			break;
		}
		return name;
	}

}
