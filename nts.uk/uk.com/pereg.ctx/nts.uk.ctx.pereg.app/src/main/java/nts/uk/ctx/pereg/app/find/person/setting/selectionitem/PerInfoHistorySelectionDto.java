package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;

@Value
public class PerInfoHistorySelectionDto {
	
	private String histId;
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
	
	public static PerInfoHistorySelectionDto createDto(DateHistoryItem dateItem) {
		return new PerInfoHistorySelectionDto(dateItem.identifier(), dateItem.start(), dateItem.end());
	}

}
