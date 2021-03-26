package nts.uk.ctx.at.function.dom.adapter;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErrorAlarmConAdapterDto;

@Getter
@NoArgsConstructor
@Builder
public class WorkRecordExtraConAdapterDto {
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
	
	private ErrorAlarmConAdapterDto errorAlarmCondition;

	public WorkRecordExtraConAdapterDto(String errorAlarmCheckID, int checkItem, boolean messageBold,
			String messageColor, int sortOrderBy, boolean useAtr, String nameWKRecord,
			ErrorAlarmConAdapterDto errorAlarmCondition) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.checkItem = checkItem;
		this.messageBold = messageBold;
		this.messageColor = messageColor;
		this.sortOrderBy = sortOrderBy;
		this.useAtr = useAtr;
		this.nameWKRecord = nameWKRecord;
		this.errorAlarmCondition = errorAlarmCondition;
	}
	
	
	
}
