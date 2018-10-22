package nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SelfConfirmErrorImport {
	/* エラーがあっても確認できる*/
	CAN_CONFIRM_WHEN_ERROR(0),
	/* エラーを訂正するまでチェックできない */
	CAN_NOT_CHECK_WHEN_ERROR(1),
	/* エラーを訂正するまで登録できない */
	CAN_NOT_REGISTER_WHEN_ERROR(2);
	
	public final int value;
}
