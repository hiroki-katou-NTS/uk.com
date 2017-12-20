package nts.uk.ctx.pereg.dom.person.info.daterangeitem;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class DateRangeItem extends AggregateRoot {
	private String personInfoCtgId;
	private String startDateItemId;
	private String endDateItemId;
	private String dateRangeItemId;

	private DateRangeItem(String personInfoCtgId, String startDateItemId, String endDateItemId,
			String dateRangeItemId) {
		super();
		this.personInfoCtgId = personInfoCtgId;
		this.startDateItemId = startDateItemId;
		this.endDateItemId = endDateItemId;
		this.dateRangeItemId = dateRangeItemId;
	}

	public static DateRangeItem createFromJavaType(String personInfoCtgId, String startDateItemId, String endDateItemId,
			String dateRangeItemId) {
		return new DateRangeItem(personInfoCtgId, startDateItemId, endDateItemId, dateRangeItemId);
	}

}
