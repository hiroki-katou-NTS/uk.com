package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

/**
 * ReadOnlyDataSource
 * 
 * @author lanlt
 *
 */
@Getter
public class ReadOnlyDataSource extends DataTypeStateDataSource {

	private String readText;

	public ReadOnlyDataSource(String readText) {
		super();
		this.dataTypeValue = DataTypeValue.READONLY.value;
		this.readText = readText;
	}

	public static ReadOnlyDataSource createFromJavaType(String readText) {
		return new ReadOnlyDataSource(readText);
	}

}