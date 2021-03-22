package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FixedConditionDataPubExport {
	/** No */
	private int fixConWorkRecordNo;
	/** 名称 */
	private String fixConWorkRecordName;
	/** 初期メッセージ*/
	private String message;
	/** 区分*/
	private Integer eralarmAtr;

	public FixedConditionDataPubExport(int fixConWorkRecordNo, String fixConWorkRecordName, String message, Integer eralarmAtr) {
		super();
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.fixConWorkRecordName = fixConWorkRecordName;
		this.message = message;
		this.eralarmAtr = eralarmAtr;
	}
	
	
}
