package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
/**
 * EnumRefConditionDataSource
 * @author lanlt
 *
 */
@Getter
public class EnumRefConditionDataSource extends SelectionItemDataSource {

	private String enumName;

	private EnumRefConditionDataSource(String enumName, int dataType) {
		super(ReferenceTypes.ENUM);
		this.dataTypeValue = dataType;
		this.enumName = enumName;
	}

	public static SelectionItemDataSource createFromJavaType(String enumName, int dataTypeValue) {
		return new EnumRefConditionDataSource(enumName, dataTypeValue);
	}
}
