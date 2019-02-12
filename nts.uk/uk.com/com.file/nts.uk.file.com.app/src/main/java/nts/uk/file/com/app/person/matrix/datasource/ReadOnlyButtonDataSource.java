package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

/**
 * 
 * @author lanlt
 *
 */
@Getter
public class ReadOnlyButtonDataSource extends DataTypeStateDataSource {
	private String readText;

	public ReadOnlyButtonDataSource(String readText) {
		super();
		this.dataTypeValue = DataTypeValue.READONLY_BUTTON.value;
		this.readText = readText;
	}

	public static ReadOnlyButtonDataSource createFromJavaType(String readText) {
		return new ReadOnlyButtonDataSource(readText);
	}

}