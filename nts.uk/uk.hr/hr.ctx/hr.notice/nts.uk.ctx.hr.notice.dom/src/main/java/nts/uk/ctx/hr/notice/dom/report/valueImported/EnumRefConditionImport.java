package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class EnumRefConditionImport extends SelectionItemImport {

	private String enumName;

	private EnumRefConditionImport(String enumName, int dataType) {
		super(ReferenceTypesImport.ENUM);
		this.dataTypeValue = dataType;
		this.enumName = enumName;
	}

	public static SelectionItemImport createFromJavaType(String enumName, int dataTypeValue) {
		return new EnumRefConditionImport(enumName, dataTypeValue);
	}
}
