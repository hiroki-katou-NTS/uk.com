/**
 * 9:37:57 AM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;

/**
 * @author hungnm
 *
 */
@Getter
public class ErrorAlarmWorkRecord extends AggregateRoot {

	/* 会社ID */
	private String companyId;

	/* コード */
	private ErrorAlarmWorkRecordCode code;

	/* 名称 */
	private ErrorAlarmWorkRecordName name;

	/* システム固定とする */
	private Boolean fixedAtr;

	/* 使用する */
	private Boolean useAtr;

	/* 区分 */
	private ErrorAlarmClassification typeAtr;

	/* メッセージ */
	private ErrorAlarmMessage message;

	/* エラーアラームを解除できる */
	private Boolean cancelableAtr;

	/* エラー表示項目 */
	private BigDecimal errorDisplayItem;
	
	/* エラー解除ロールID */
	private String cancelRoleId;

	/* Constructor */
	private ErrorAlarmWorkRecord() {
		super();
	}

	/* Constructor */
	private ErrorAlarmWorkRecord(String companyId, ErrorAlarmWorkRecordCode code, ErrorAlarmWorkRecordName name,
			boolean fixedAtr, boolean useAtr, ErrorAlarmClassification typeAtr, ErrorAlarmMessage message,
			boolean cancelableAtr, BigDecimal errorDisplayItem) {
		super();
		this.companyId = companyId;
		this.code = code;
		this.name = name;
		this.fixedAtr = fixedAtr;
		this.useAtr = useAtr;
		this.typeAtr = typeAtr;
		this.message = message;
		this.cancelableAtr = cancelableAtr;
		this.errorDisplayItem = errorDisplayItem;
	}

	/**
	 * Create ErrorAlarmWorkRecord from Java type
	 * 
	 * @param
	 * @return ErrorAlarmWorkRecord
	 **/
	public static ErrorAlarmWorkRecord createFromJavaType(String companyId, String code, String name, boolean fixedAtr,
			boolean useAtr, int typeAtr, String displayMessage, boolean boldAtr, String messageColor,
			boolean cancelableAtr, BigDecimal errorDisplayItem) {
		return new ErrorAlarmWorkRecord(companyId, new ErrorAlarmWorkRecordCode(code),
				new ErrorAlarmWorkRecordName(name), fixedAtr, useAtr,
				EnumAdaptor.valueOf(typeAtr, ErrorAlarmClassification.class),
				ErrorAlarmMessage.createFromJavaType(displayMessage, boldAtr, messageColor), cancelableAtr,
				errorDisplayItem);
	}
}
