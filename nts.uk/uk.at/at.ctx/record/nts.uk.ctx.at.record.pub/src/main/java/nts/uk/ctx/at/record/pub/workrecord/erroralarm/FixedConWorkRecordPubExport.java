package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FixedConWorkRecordPubExport {
	/** 日次のアラームチェック条件ID */
	private String dailyAlarmConID;
	/** No */
	private int fixConWorkRecordNo;
	/** メッセージ */
	private String message;
	/** 使用区分 */
	private boolean useAtr;
	
	public FixedConWorkRecordPubExport(String dailyAlarmConID, int fixConWorkRecordNo, String message, boolean useAtr) {
		super();
		this.dailyAlarmConID = dailyAlarmConID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.message = message;
		this.useAtr = useAtr;
	}
	
}
