package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErrorAlarmConditionPubExport;

@Getter

@NoArgsConstructor
public class WorkRecordExtraConPubExport {
	@Setter
	private String errorAlarmCheckID;
	//TypeCheckWorkRecord
	private int checkItem;
	
	private boolean messageBold;
	//ColorCode
	private String messageColor;
	
	private int sortOrderBy;
	
	private boolean useAtr;
	//NameWKRecord
	private String nameWKRecord;
	
	private ErrorAlarmConditionPubExport errorAlarmCondition;
	
	public WorkRecordExtraConPubExport(String errorAlarmCheckID, int checkItem, boolean messageBold,
			String messageColor, int sortOrderBy, boolean useAtr, String nameWKRecord) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkItem = checkItem;
		this.messageBold = messageBold;
		this.messageColor = messageColor;
		this.sortOrderBy = sortOrderBy;
		this.useAtr = useAtr;
		this.nameWKRecord = nameWKRecord;
	}
	
	public void setErrorAlarmCondition(ErrorAlarmConditionPubExport errorAlarmCondition) {
		this.errorAlarmCondition = errorAlarmCondition;
	}
	
}
