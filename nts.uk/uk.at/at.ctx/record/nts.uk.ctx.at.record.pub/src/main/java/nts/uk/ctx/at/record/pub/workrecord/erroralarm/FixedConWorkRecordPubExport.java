package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FixedConWorkRecordPubExport {
	/** 日次のアラームチェック条件ID */
	private String errorAlarmID;
	/** No */
	private int fixConWorkRecordNo;
	/** メッセージ */
	private String message;
	/** 使用区分 */
	private boolean useAtr;
	
	public FixedConWorkRecordPubExport(String errorAlarmID, int fixConWorkRecordNo, String message, boolean useAtr) {
		super();
		this.errorAlarmID = errorAlarmID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.message = message;
		this.useAtr = useAtr;
	}
	
}
