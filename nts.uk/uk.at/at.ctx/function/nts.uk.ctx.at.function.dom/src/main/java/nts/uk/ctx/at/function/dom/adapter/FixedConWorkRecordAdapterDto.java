package nts.uk.ctx.at.function.dom.adapter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FixedConWorkRecordAdapterDto {
	/** 日次のアラームチェック条件ID */
	private String errorAlarmID;
	/** No */
	private int fixConWorkRecordNo;
	/** メッセージ */
	private String message;
	/** 使用区分 */
	private boolean useAtr;
	
	public FixedConWorkRecordAdapterDto(String errorAlarmID, int fixConWorkRecordNo, String message, boolean useAtr) {
		super();
		this.errorAlarmID = errorAlarmID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.message = message;
		this.useAtr = useAtr;
	}
	
}
