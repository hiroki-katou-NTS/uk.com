package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.algorithm;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;

@AllArgsConstructor
@Value
public class FixExtracItem {
	// NO
	private int no;
	
	//　名称
	private String name;
	
	// メッセージ
	private String message;
}
