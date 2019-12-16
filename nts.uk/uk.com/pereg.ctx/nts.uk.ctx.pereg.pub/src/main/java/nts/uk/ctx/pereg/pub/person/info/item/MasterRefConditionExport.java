package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class MasterRefConditionExport extends SelectionItemExport {

	private String masterType;

	private MasterRefConditionExport(String masterType, int dataTypeValue) {
		super(ReferenceTypesExport.DESIGNATED_MASTER);
		this.masterType = masterType;
		this.dataTypeValue = dataTypeValue;
	}

	public static SelectionItemExport createFromJavaType(String masterType, int dataTypeValue) {
		return new MasterRefConditionExport(masterType, dataTypeValue);
	}
}
