package nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * マスタチェックの固有抽出項目
 */
@Getter
public class MasterCheckFixedExtractItem extends AggregateRoot {

	private int no;
	
	private ErrorAlarmMessage initMessage;
	
	private ErrorAlarmAtr erAlAtr;
	
	private MasterCheckFixedCheckItem name;

	public MasterCheckFixedExtractItem(int no, ErrorAlarmMessage initMessage, ErrorAlarmAtr erAlAtr,
			MasterCheckFixedCheckItem name) {
		this.no = no;
		this.initMessage = initMessage;
		this.erAlAtr = erAlAtr;
		this.name = name;
	}
}
