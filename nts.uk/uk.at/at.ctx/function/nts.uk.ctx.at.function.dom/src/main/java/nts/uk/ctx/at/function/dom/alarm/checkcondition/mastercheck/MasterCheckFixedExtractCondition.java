package nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * マスタチェックの固定抽出条件
 */
@Getter
public class MasterCheckFixedExtractCondition extends AggregateRoot {

	/**
	 * エラーアラームチェックID
	 */
	private String errorAlarmCheckId;
	
	private int no;
	
	private ErrorAlarmMessageMSTCHK message;
	
	private boolean useAtr;

	public MasterCheckFixedExtractCondition(String errorAlarmCheckId, int no, ErrorAlarmMessageMSTCHK message,
			boolean useAtr) {
		this.errorAlarmCheckId = errorAlarmCheckId;
		this.no = no;
		this.message = message;
		this.useAtr = useAtr;
	}
}
