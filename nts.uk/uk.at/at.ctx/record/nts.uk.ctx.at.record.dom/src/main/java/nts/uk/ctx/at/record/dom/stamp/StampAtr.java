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
	// 6:応援開始
	SUPPORT_START(6, "応援開始"),
	// 7:臨時開始
	EMERGENCY_START(7, "臨時開始"),
	// 8:応援終了
	SUPPORT_END(8, "応援終了"),
	// 9:臨時終了
	EMERGENCY_END(9, "臨時終了"),
	// 10: PCログオン
	PCLOGON(10, "PCログオン"),
	//11: PCログオフ
	PCLOGOFF(11, "PCログオフ");

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
			name = "応援開始";
			break;
		case 7:
			name = "臨時開始";
			break;
		case 8:
			name = "応援終了";
			break;
		case 9:
			name = "臨時終了";
			break;
		case 10:
			name = "PCログオン";
			break;
		case 11:
			name = "PCログオフ";
			break;
		default:
			name = "出勤";
			break;
		}
		return name;
	}

}
