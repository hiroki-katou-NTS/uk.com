package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck;

import java.util.Optional;

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
	
	private MasterCheckFixedCheckItem no;
	
	private Optional<ErrorAlarmMessageMSTCHK> message;
	
	private boolean useAtr;

	public MasterCheckFixedExtractCondition(String errorAlarmCheckId, MasterCheckFixedCheckItem no, Optional<ErrorAlarmMessageMSTCHK> message,
			boolean useAtr) {
		this.errorAlarmCheckId = errorAlarmCheckId;
		this.no = no;
		this.message = message;
		this.useAtr = useAtr;
	}
}
