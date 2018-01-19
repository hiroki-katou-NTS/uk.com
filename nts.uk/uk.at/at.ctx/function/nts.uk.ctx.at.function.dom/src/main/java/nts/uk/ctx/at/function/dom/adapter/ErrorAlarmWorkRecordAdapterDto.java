package nts.uk.ctx.at.function.dom.adapter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorAlarmWorkRecordAdapterDto {

	/* 会社ID */
	private String companyId;
	/* コード */
	private String code;
	/* 名称 */
	private String name;
	/* システム固定とする */
	private int fixedAtr;
	/* 使用する */
	private int useAtr;
	/* 区分 */
	private int typeAtr;

	/* メッセージを太字にする */
	private int boldAtr;
	/* メッセージの色 */
	private String messageColor;
	/* エラーアラームを解除できる */
	private int cancelableAtr;
	/* エラー表示項目 */
	private int errorDisplayItem;
	/* エラー解除ロールID */
	private String cancelRoleId;
	
	private String errorAlarmCheckID;

	public ErrorAlarmWorkRecordAdapterDto(String companyId, String code, String name, int fixedAtr, int useAtr,
			int typeAtr, int boldAtr, String messageColor, int cancelableAtr, int errorDisplayItem, String cancelRoleId,
			String errorAlarmCheckID) {
		super();
		this.companyId = companyId;
		this.code = code;
		this.name = name;
		this.fixedAtr = fixedAtr;
		this.useAtr = useAtr;
		this.typeAtr = typeAtr;
		this.boldAtr = boldAtr;
		this.messageColor = messageColor;
		this.cancelableAtr = cancelableAtr;
		this.errorDisplayItem = errorDisplayItem;
		this.cancelRoleId = cancelRoleId;
		this.errorAlarmCheckID = errorAlarmCheckID;
	}
	
	
}
