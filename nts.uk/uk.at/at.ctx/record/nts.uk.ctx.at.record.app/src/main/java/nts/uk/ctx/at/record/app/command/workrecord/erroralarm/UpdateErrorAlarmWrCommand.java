/**
 * 9:44:13 AM Jul 24, 2017
 */
package nts.uk.ctx.at.record.app.command.workrecord.erroralarm;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class UpdateErrorAlarmWrCommand {

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

	/* 表示メッセージ */
	private String displayMessage;

	/* メッセージを太字にする */
	private int boldAtr;

	/* メッセージの色 */
	private String messageColor;

	/* エラーアラームを解除できる */
	private int cancelableAtr;

	/* エラー表示項目 */
	private BigDecimal errorDisplayItem;

	public UpdateErrorAlarmWrCommand() {
		super();
	}

	public UpdateErrorAlarmWrCommand(String companyId, String code, String name, int fixedAtr, int useAtr, int typeAtr,
			String displayMessage, int boldAtr, String messageColor, int cancelableAtr, BigDecimal errorDisplayItem) {
		super();
		this.companyId = companyId;
		this.code = code;
		this.name = name;
		this.fixedAtr = fixedAtr;
		this.useAtr = useAtr;
		this.typeAtr = typeAtr;
		this.displayMessage = displayMessage;
		this.boldAtr = boldAtr;
		this.messageColor = messageColor;
		this.cancelableAtr = cancelableAtr;
		this.errorDisplayItem = errorDisplayItem;
	}

	public ErrorAlarmWorkRecord toDomain() {
		return ErrorAlarmWorkRecord.createFromJavaType(this.companyId, this.code, this.name,
				this.fixedAtr == 1 ? true : false, this.useAtr == 1 ? true : false, this.typeAtr, this.displayMessage,
				this.boldAtr == 1 ? true : false, this.messageColor, this.cancelableAtr == 1 ? true : false,
				this.errorDisplayItem);
	}

}
