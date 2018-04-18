package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SelfConfirmError {
	/* エラーがあっても確認できる*/
	CAN_CONFIRM_WHEN_ERROR(0),
	/* エラーを訂正するまでチェックできない */
	CAN_NOT_CHECK_WHEN_ERROR(1),
	/* エラーを訂正するまで登録できない */
	CAN_NOT_REGISTER_WHEN_ERROR(2);
	
	public final int value;
}
