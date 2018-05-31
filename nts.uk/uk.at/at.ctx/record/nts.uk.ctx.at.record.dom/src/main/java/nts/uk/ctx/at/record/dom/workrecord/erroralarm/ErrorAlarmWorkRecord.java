/**
 * 9:37:57 AM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;

/**
 * @author hungnm 勤務実績のエラーアラーム
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
	private Integer errorDisplayItem;

	/* エラー解除ロールID */
	private String cancelRoleId;

	private List<Integer> lstApplication;

	private String errorAlarmCheckID;
	
	@Setter
	private ErrorAlarmCondition errorAlarmCondition;

	/* Constructor */
	private ErrorAlarmWorkRecord() {
		super();
	}

	/* Constructor */
	private ErrorAlarmWorkRecord(String companyId, ErrorAlarmWorkRecordCode code, ErrorAlarmWorkRecordName name,
			boolean fixedAtr, boolean useAtr, ErrorAlarmClassification typeAtr, ErrorAlarmMessage message,
			boolean cancelableAtr, Integer errorDisplayItem, List<Integer> lstApplication,
			String errorAlarmCheckID) {
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
		this.lstApplication = lstApplication;
		this.errorAlarmCheckID = errorAlarmCheckID;
	}

	/**
	 * Create ErrorAlarmWorkRecord from Java type
	 * 
	 * @param
	 * @return ErrorAlarmWorkRecord
	 **/
	public static ErrorAlarmWorkRecord createFromJavaType(String companyId, String code, String name, boolean fixedAtr,
			boolean useAtr, int typeAtr, boolean boldAtr, String messageColor, boolean cancelableAtr,
			Integer errorDisplayItem, List<Integer> lstApplication, String errorAlarmCheckID) {
		ErrorAlarmWorkRecord errorAlarmWorkRecord = new ErrorAlarmWorkRecord(companyId,
				new ErrorAlarmWorkRecordCode(code), new ErrorAlarmWorkRecordName(name), fixedAtr, useAtr,
				EnumAdaptor.valueOf(typeAtr, ErrorAlarmClassification.class),
				ErrorAlarmMessage.createFromJavaType(boldAtr, messageColor), cancelableAtr, errorDisplayItem,
				lstApplication, errorAlarmCheckID);
		return errorAlarmWorkRecord;
	}

	/**
	 * Init
	 * 
	 * @param
	 * @return ErrorAlarmWorkRecord
	 **/
	public static ErrorAlarmWorkRecord init(String companyId, String code, String name, boolean fixedAtr,
			boolean useAtr, int typeAtr, boolean boldAtr, String messageColor, boolean cancelableAtr,
			Integer errorDisplayItem, List<Integer> lstApplication) {
		ErrorAlarmWorkRecord errorAlarmWorkRecord = new ErrorAlarmWorkRecord(companyId,
				code.length() < 4 ? new ErrorAlarmWorkRecordCode("U" + code) : new ErrorAlarmWorkRecordCode(code),
				new ErrorAlarmWorkRecordName(name), fixedAtr, useAtr,
				EnumAdaptor.valueOf(typeAtr, ErrorAlarmClassification.class),
				ErrorAlarmMessage.createFromJavaType(boldAtr, messageColor), cancelableAtr, errorDisplayItem,
				lstApplication, IdentifierUtil.randomUniqueId());
		return errorAlarmWorkRecord;
	}

	public void setCheckId(String errorAlarmCheckID) {
		this.errorAlarmCheckID = errorAlarmCheckID;
	}
	

}

