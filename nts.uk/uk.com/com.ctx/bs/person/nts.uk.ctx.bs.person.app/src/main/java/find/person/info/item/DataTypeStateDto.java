package find.person.info.item;

import lombok.Value;

@Value
public class DataTypeStateDto {
	private TimeItemDto timeItem;
	private StringItemDto stringItem;
	private TimePointItemDto timePointItem;
	private DateItemDto dateItem;
	private NumericItemDto numericItem;
	private SelectionItemDto selectionItem;
}
