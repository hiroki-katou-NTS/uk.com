package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Value;

@Value
public class ErrorAlarmWorkRecordDto {

	/* 会社ID */
	private String companyId;

	/* コード */
	private String code;

	/* 名称 */
	private String name;

	/* システム固定とする */
	private boolean fixedAtr;

	/* 使用する */
	private boolean useAtr;

	/* 区分 */
	private int typeAtr;

	/* 表示メッセージ */
	private String eralCheckId;

	/* メッセージを太字にする */
	private boolean boldAtr;

	/* メッセージの色 */
	private String messageColor;

	/* エラーアラームを解除できる */
	private boolean cancelableAtr;

	/* エラー表示項目 */
	private Integer errorDisplayItem;
}
