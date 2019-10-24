package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorAlarmDtoMob {
	/* コード */
	private String code;

	/* 名称 */
	private String name;

	/*エラーメッセージ */
	private String errMsg;
}
