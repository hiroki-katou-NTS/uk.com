package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class EnumRefConditionExport extends SelectionItemExport {

	private String enumName;

	private EnumRefConditionExport(String enumName, int dataType) {
		super(ReferenceTypesExport.ENUM);
		this.dataTypeValue = dataType;
		this.enumName = enumName;
	}

	public static SelectionItemExport createFromJavaType(String enumName, int dataTypeValue) {
		return new EnumRefConditionExport(enumName, dataTypeValue);
	}
}
