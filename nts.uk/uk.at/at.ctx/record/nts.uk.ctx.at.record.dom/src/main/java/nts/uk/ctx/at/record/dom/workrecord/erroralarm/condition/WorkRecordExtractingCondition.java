package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.NameWKRecord;
/**
 * 勤務実績の抽出条件
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class WorkRecordExtractingCondition extends AggregateRoot {
	
	private String errorAlarmCheckID;
	
	private TypeCheckWorkRecord checkItem;
	
	private DisplayMessages displayMessages;
	
	private int sortOrderBy;
	
	private boolean useAtr;
	
	private NameWKRecord nameWKRecord;

	public WorkRecordExtractingCondition(String errorAlarmCheckID, TypeCheckWorkRecord checkItem,
			DisplayMessages displayMessages, int sortOrderBy, boolean useAtr, NameWKRecord nameWKRecord) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkItem = checkItem;
		this.displayMessages = displayMessages;
		this.sortOrderBy = sortOrderBy;
		this.useAtr = useAtr;
		this.nameWKRecord = nameWKRecord;
	}

	
	
	

}
