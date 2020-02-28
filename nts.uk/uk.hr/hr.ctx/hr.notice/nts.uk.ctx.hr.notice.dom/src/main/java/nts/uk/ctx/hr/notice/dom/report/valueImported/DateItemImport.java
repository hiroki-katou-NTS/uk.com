package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class DateItemImport extends DataTypeStateImport {

	private int dateItemType;

	private DateItemImport(int dateItemType) {
		super();
		this.dataTypeValue = DataTypeValueImport.DATE.value;
		this.dateItemType = dateItemType;
	}

	public static DateItemImport createFromJavaType(int dateItemType) {
		return new DateItemImport(dateItemType);
	}
}
