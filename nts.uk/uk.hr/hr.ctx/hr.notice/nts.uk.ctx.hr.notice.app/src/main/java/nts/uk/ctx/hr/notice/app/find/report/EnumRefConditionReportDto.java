package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class EnumRefConditionReportDto  extends SelectionItemReportDto{

	private String enumName;

	private EnumRefConditionReportDto(String enumName, int dataType) {
		super(ReferenceTypesReport.ENUM);
		this.dataTypeValue = dataType;
		this.enumName = enumName;
	}

	public static SelectionItemReportDto createFromJavaType(String enumName, int dataTypeValue) {
		return new EnumRefConditionReportDto(enumName, dataTypeValue);
	}
	
}
