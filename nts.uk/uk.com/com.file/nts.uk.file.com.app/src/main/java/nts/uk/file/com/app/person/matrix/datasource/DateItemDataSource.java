package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class DateItemDataSource extends DataTypeStateDataSource {

	private int dateItemType;

	private DateItemDataSource(int dateItemType) {
		super();
		this.dataTypeValue = DataTypeValue.DATE.value;
		this.dateItemType = dateItemType;
	}

	public static DateItemDataSource createFromJavaType(int dateItemType) {
		return new DateItemDataSource(dateItemType);
	}
}
