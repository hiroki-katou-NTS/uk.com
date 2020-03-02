package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DateRangeItemExport {
	private String personInfoCtgId;
	private String startDateItemId;
	private String endDateItemId;
	private String dateRangeItemId;
}
