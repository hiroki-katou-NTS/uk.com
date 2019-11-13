package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class StringItemReportDto extends DataTypeStateReportDto {
	private int stringItemLength;
	private int stringItemType;
	private int stringItemDataType;

	private StringItemReportDto(int stringItemLength, int stringItemType, int stringItemDataType) {
		super();
		this.dataTypeValue = DataTypeValueReport.STRING.value;
		this.stringItemLength = stringItemLength;
		this.stringItemType = stringItemType;
		this.stringItemDataType = stringItemDataType;
	}

	public static StringItemReportDto createFromJavaType(int stringItemLength, int stringItemType,
			int stringItemDataType) {
		return new StringItemReportDto(stringItemLength, stringItemType, stringItemDataType);
	}

	public void updateStringLength(int length) {
		this.stringItemLength = length;
	}
}
