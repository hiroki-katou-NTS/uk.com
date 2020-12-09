package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixConWorkRecordName;

/**
 * マスタチェックの固有抽出項目
 */
@Getter
public class MasterCheckFixedExtractItem extends AggregateRoot {

	private MasterCheckFixedCheckItem no;
	
	private ErrorAlarmMessageMSTCHK initMessage;
	
	private ErrorAlarmAtr erAlAtr;
	
	private FixConWorkRecordName name;

	public MasterCheckFixedExtractItem(MasterCheckFixedCheckItem no, ErrorAlarmMessageMSTCHK initMessage, ErrorAlarmAtr erAlAtr,
			FixConWorkRecordName name) {
		this.no = no;
		this.initMessage = initMessage;
		this.erAlAtr = erAlAtr;
		this.name = name;
	}
}
