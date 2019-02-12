package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

/**
 * CodeNameRefTypeDataSource
 * 
 * @author lanlt
 *
 */
@Getter
public class CodeNameRefTypeDataSource extends SelectionItemDataSource {

	private String typeCode;
	private String selectionItemName;

	private CodeNameRefTypeDataSource(String typeCode, String selectionItemName) {
		super(ReferenceTypes.CODE_NAME);
		this.typeCode = typeCode;
		this.selectionItemName = selectionItemName;
	}

	private CodeNameRefTypeDataSource(String typeCode, int dataTypeValue) {
		super(ReferenceTypes.CODE_NAME);
		this.typeCode = typeCode;
		this.dataTypeValue = dataTypeValue;
	}

	public static CodeNameRefTypeDataSource createFromJavaType(String typeCode, String selectionItemName) {
		return new CodeNameRefTypeDataSource(typeCode, selectionItemName);
	}

	public static SelectionItemDataSource createFromJavaType(String typeCode, int dataTypeValue) {
		return new CodeNameRefTypeDataSource(typeCode, dataTypeValue);
	}
}
