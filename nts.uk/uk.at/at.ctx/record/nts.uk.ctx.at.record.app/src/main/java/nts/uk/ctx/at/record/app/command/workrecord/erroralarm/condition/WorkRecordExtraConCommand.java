package nts.uk.ctx.at.record.app.command.workrecord.erroralarm.condition;

//import lombok.NoArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.DisplayMessages;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.NameWKRecord;

@Value
public class WorkRecordExtraConCommand {

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
	public WorkRecordExtraConCommand(String errorAlarmCheckID, int checkItem, boolean messageBold, String messageColor,
			int sortOrderBy, boolean useAtr, String nameWKRecord) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkItem = checkItem;
		this.messageBold = messageBold;
		this.messageColor = messageColor;
		this.sortOrderBy = sortOrderBy;
		this.useAtr = useAtr;
		this.nameWKRecord = nameWKRecord;
	} 
	
	public WorkRecordExtractingCondition fromDomain() {
		return new WorkRecordExtractingCondition(
			this.errorAlarmCheckID,
			EnumAdaptor.valueOf(this.checkItem, TypeCheckWorkRecord.class),
			new DisplayMessages(this.messageBold,new ColorCode(this.messageColor)),
			this.sortOrderBy,
			this.useAtr,
			new NameWKRecord(this.nameWKRecord)
				);
	}
	
	
}
