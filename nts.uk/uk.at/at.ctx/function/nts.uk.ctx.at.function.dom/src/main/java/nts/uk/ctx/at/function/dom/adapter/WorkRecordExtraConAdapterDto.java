package nts.uk.ctx.at.function.dom.adapter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkRecordExtraConAdapterDto {
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
	public WorkRecordExtraConAdapterDto(String errorAlarmCheckID, int checkItem, boolean messageBold,
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
	
	
}
