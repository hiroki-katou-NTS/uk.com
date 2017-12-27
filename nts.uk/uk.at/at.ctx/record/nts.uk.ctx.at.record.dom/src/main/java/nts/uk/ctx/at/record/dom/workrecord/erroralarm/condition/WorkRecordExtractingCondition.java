package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.NameWKRecord;
/**
 * 勤務実績の抽出条件
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class WorkRecordExtractingCondition extends DomainObject {
	
	private String errorAlarmCheckID;
	
	private int checkItem;
	
	private boolean messageBold;
	
	private ColorCode messageColor;
	
	private int sortOrderBy;
	
	private boolean useAtr;
	
	private NameWKRecord nameWKRecord;

	public WorkRecordExtractingCondition(String errorAlarmCheckID, int checkItem, boolean messageBold,
			ColorCode messageColor, int sortOrderBy, boolean useAtr, NameWKRecord nameWKRecord) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkItem = checkItem;
		this.messageBold = messageBold;
		this.messageColor = messageColor;
		this.sortOrderBy = sortOrderBy;
		this.useAtr = useAtr;
		this.nameWKRecord = nameWKRecord;
	}
	
	

}
