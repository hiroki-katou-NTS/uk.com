package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;

@Value
@AllArgsConstructor
public class DateRangeItemDto {
	private String personInfoCtgId;
	private String startDateItemId;
	private String endDateItemId;
	private String dateRangeItemId;
	
	public static DateRangeItemDto createObjectFromObjectDomain(DateRangeItem domain){
		return new DateRangeItemDto(domain.getPersonInfoCtgId(), domain.getStartDateItemId(),
				domain.getEndDateItemId(), domain.getDateRangeItemId());
	}
}
