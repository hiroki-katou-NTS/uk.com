package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class DateItemExport extends DataTypeStateExport {

	private int dateItemType;

	private DateItemExport(int dateItemType) {
		super();
		this.dataTypeValue = DataTypeValueExport.DATE.value;
		this.dateItemType = dateItemType;
	}

	public static DateItemExport createFromJavaType(int dateItemType) {
		return new DateItemExport(dateItemType);
	}
}
