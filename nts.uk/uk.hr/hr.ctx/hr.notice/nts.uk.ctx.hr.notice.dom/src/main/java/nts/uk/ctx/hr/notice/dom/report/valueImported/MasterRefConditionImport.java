package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class MasterRefConditionImport extends SelectionItemImport {

	private String masterType;

	private MasterRefConditionImport(String masterType, int dataTypeValue) {
		super(ReferenceTypesImport.DESIGNATED_MASTER);
		this.masterType = masterType;
		this.dataTypeValue = dataTypeValue;
	}

	public static SelectionItemImport createFromJavaType(String masterType, int dataTypeValue) {
		return new MasterRefConditionImport(masterType, dataTypeValue);
	}
}
