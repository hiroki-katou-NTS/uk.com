package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.DisplayMessages;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.NameWKRecord;

@Data
@NoArgsConstructor
public class WorkRecordExtraConDto {
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
	
	public WorkRecordExtraConDto(String errorAlarmCheckID, int checkItem, boolean messageBold, String messageColor,
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
	
	public static WorkRecordExtraConDto fromDomain(WorkRecordExtractingCondition domain) {
		return new WorkRecordExtraConDto(
				domain.getErrorAlarmCheckID(),
				domain.getCheckItem().value,
				domain.getDisplayMessages().isMessageBold(),
				domain.getDisplayMessages().getMessageColor().v(),
				domain.getSortOrderBy(),
				domain.isUseAtr(),
				domain.getNameWKRecord().v()
				);
	}
	
	public WorkRecordExtractingCondition toEntity() {
		return new WorkRecordExtractingCondition(
				this.errorAlarmCheckID,
				EnumAdaptor.valueOf(this.checkItem, TypeCheckWorkRecord.class),
				new DisplayMessages(
					this.messageBold,
					new ColorCode(this.messageColor)),
				this.sortOrderBy,
				this.useAtr,
				new NameWKRecord(this.nameWKRecord)
				);
	}
	
}
